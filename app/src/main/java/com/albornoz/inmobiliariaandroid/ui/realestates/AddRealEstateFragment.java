package com.albornoz.inmobiliariaandroid.ui.realestates;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.albornoz.inmobiliariaandroid.R;
import com.albornoz.inmobiliariaandroid.databinding.FragmentAddRealEstateBinding;

public class AddRealEstateFragment extends Fragment {

    private AddRealEstateViewModel mViewModel;
    private FragmentAddRealEstateBinding binding;

    // Lanzadores para la galería y el permiso
    private ActivityResultLauncher<Intent> galleryLauncher;
    private ActivityResultLauncher<String> requestPermissionLauncher;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Usar View Binding para inflar el layout
        binding = FragmentAddRealEstateBinding.inflate(inflater, container, false);
        binding.btnRemoveImage.setVisibility(View.GONE);
        View view = binding.getRoot();
        mViewModel = new ViewModelProvider(this).get(AddRealEstateViewModel.class);

        // Configurar los spinners usando View Binding
        setupSpinners();

        // Configurar los lanzadores para la galería y permisos
        setupGalleryLauncher();

        // Listener para el botón de seleccionar imagen
        binding.btnAddImage.setOnClickListener(v -> checkGalleryPermissionAndOpenGallery());

        // Listener para el botón de eliminar imagen
        binding.btnRemoveImage.setOnClickListener(v -> mViewModel.removeSelectedImage());

        // Listener para el botón Guardar
        binding.btnSave.setOnClickListener(v -> mViewModel.uploadRealEstate(
                binding.spinnerTipo.getSelectedItem().toString(),
                binding.spinnerUso.getSelectedItem().toString(),
                binding.editDireccion.getText().toString(),
                binding.editPrecio.getText().toString(),
                binding.editAmbientes.getText().toString()
        ));

        // Observar los cambios en la imagen seleccionada en el ViewModel
        observeViewModel();

        return view;
    }

    private void setupSpinners() {
        // Configurar Spinner para "Tipo"
        ArrayAdapter<CharSequence> adapterTipo = ArrayAdapter.createFromResource(getContext(),
                R.array.tipos_inmueble, android.R.layout.simple_spinner_item);
        adapterTipo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerTipo.setAdapter(adapterTipo);

        // Configurar Spinner para "Uso"
        ArrayAdapter<CharSequence> adapterUso = ArrayAdapter.createFromResource(getContext(),
                R.array.usos_inmueble, android.R.layout.simple_spinner_item);
        adapterUso.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerUso.setAdapter(adapterUso);
    }

    private void setupGalleryLauncher() {
        // Registrar el callback para abrir la galería
        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == getActivity().RESULT_OK && result.getData() != null) {
                        Uri imageUri = result.getData().getData();
                        mViewModel.setSelectedImageUri(imageUri);  // Pasar la URI al ViewModel
                    }
                });

        // Registrar el callback para manejar la respuesta al permiso solicitado
        requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
            if (isGranted) {
                openGallery();
            } else {
                Toast.makeText(getContext(), "Permiso a galería denegado", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void checkGalleryPermissionAndOpenGallery() {
        // Verificar si el permiso ya fue otorgado
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Toast.makeText(getContext(), "Se requiere permiso para acceder a la galería", Toast.LENGTH_LONG).show();
        } else {
            requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        galleryLauncher.launch(intent);
    }

    private void observeViewModel() {
        // Observar los cambios en el MutableLiveData del ViewModel
        mViewModel.getSelectedImageBitmap().observe(getViewLifecycleOwner(), bitmap -> {
            if (bitmap != null) {
                binding.ivImage.setImageBitmap(bitmap);  // Mostrar la imagen seleccionada
                binding.btnRemoveImage.setVisibility(View.VISIBLE);  // Mostrar el botón "Quitar Imagen"
            } else {
                binding.ivImage.setImageBitmap(null);  // Quitar la imagen
                binding.btnRemoveImage.setVisibility(View.GONE);  // Ocultar el botón "Quitar Imagen"
                Toast.makeText(getContext(), "Imagen eliminada", Toast.LENGTH_SHORT).show();
            }
        });

        // Observar la bandera que indica si el inmueble fue creado, entonces ir a fragment de detalles
        mViewModel.getOpenSavedRealEstate().observe(getViewLifecycleOwner(), success -> {
            if (success) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("realEstate", mViewModel.getRealEstate().getValue());
                NavController navController = NavHostFragment.findNavController(this);
                navController.navigate(R.id.action_addRealEstateFragment_to_realEstateDetailsFragment, bundle);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Evitar memory leaks
    }
}
