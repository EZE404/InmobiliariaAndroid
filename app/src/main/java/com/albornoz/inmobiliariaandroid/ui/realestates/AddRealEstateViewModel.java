package com.albornoz.inmobiliariaandroid.ui.realestates;

import android.app.Application;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.albornoz.inmobiliariaandroid.request.ApiClientRetrofit;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddRealEstateViewModel extends AndroidViewModel {

    private ApiClientRetrofit.InmobiliariaService service;
    private final MutableLiveData<Bitmap> selectedImageBitmap = new MutableLiveData<>();

    public AddRealEstateViewModel(@NonNull Application application) {
        super(application);
        service = ApiClientRetrofit.getInmobiliariaService(application.getApplicationContext());
    }

    // Metodo para actualizar la imagen seleccionada en el ImageView
    public void setSelectedImageUri(Uri imageUri) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), imageUri);
            selectedImageBitmap.setValue(bitmap);  // Actualizar el MutableLiveData con el bitmap
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Obtener el LiveData de la imagen seleccionada
    public LiveData<Bitmap> getSelectedImageBitmap() {
        return selectedImageBitmap;
    }

    // Metodo para eliminar la imagen seleccionada
    public void removeSelectedImage() {
        selectedImageBitmap.setValue(null);  // Remover el bitmap
    }

    // Redimensionar la imagen antes de subirla
    private Bitmap resizeBitmap(Bitmap bitmap, int maxWidth, int maxHeight) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float ratio = Math.min((float) maxWidth / width, (float) maxHeight / height);
        int newWidth = Math.round(ratio * width);
        int newHeight = Math.round(ratio * height);
        return Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
    }

    // Convierte el bitmap a un archivo antes de subirlo
    private File convertBitmapToFile(Bitmap bitmap) throws IOException {
        File file = new File(getApplication().getCacheDir(), "real_estate_image.jpg");
        FileOutputStream fos = new FileOutputStream(file);
        resizeBitmap(bitmap, 1280, 720).compress(Bitmap.CompressFormat.JPEG, 100, fos);
        fos.flush();
        fos.close();
        return file;
    }

    // Subir imagen y datos del inmueble
    public void uploadRealEstate(String tipo, String uso, String direccion, String precio, String ambientes) {
        // TODO: SI QUEDA TIEMPO, VALIDAR QUE TODOS LOS CAMPOS ESTEN LLENOS
        Bitmap imageBitmap = selectedImageBitmap.getValue();
        try {
            MultipartBody.Part imagePart = null;
            if (selectedImageBitmap.getValue() != null) {
                File imageFile = convertBitmapToFile(imageBitmap);
                RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), imageFile);
                // MultipartBody.Part es necesario para subir archivos
                imagePart = MultipartBody.Part.createFormData("ImageFile", imageFile.getName(), requestFile);
            }

            // Crear RequestBody para cada parte
            RequestBody tipoBody = RequestBody.create(MediaType.parse("text/plain"), tipo);
            RequestBody usoBody = RequestBody.create(MediaType.parse("text/plain"), uso);
            RequestBody direccionBody = RequestBody.create(MediaType.parse("text/plain"), direccion);
            RequestBody precioBody = RequestBody.create(MediaType.parse("text/plain"), precio);
            RequestBody ambientesBody = RequestBody.create(MediaType.parse("text/plain"), ambientes);

            service.crearInmueble(tipoBody, usoBody, direccionBody, precioBody, ambientesBody, imagePart)
                    .enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(getApplication(), "Inmueble subido correctamente", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplication(), "Error al subir inmueble", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(getApplication(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplication(), "Error al preparar la imagen", Toast.LENGTH_SHORT).show();
        }
    }

}
