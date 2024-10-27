package com.albornoz.inmobiliariaandroid.request;

import android.content.Context;
import android.content.SharedPreferences;

import com.albornoz.inmobiliariaandroid.modelo.Contrato;
import com.albornoz.inmobiliariaandroid.modelo.Inmueble;
import com.albornoz.inmobiliariaandroid.modelo.Inquilino;
import com.albornoz.inmobiliariaandroid.modelo.Pago;
import com.albornoz.inmobiliariaandroid.modelo.Propietario;
import com.albornoz.inmobiliariaandroid.tools.DateDeserializer;
import com.albornoz.inmobiliariaandroid.tools.DateSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public class ApiClientRetrofit {

    // URL base de la API
    private static final String HOST = "http://192.168.1.111";
    private static final String PORT = "5000";
    private static final String PATH = "api/";
    private static final String BASE_URL = HOST + ":" + PORT + "/" + PATH;

    private static InmobiliariaService inmobiliariaService;

    // Para almacenar el token JWT en SharedPreferences
    private static SharedPreferences sharedPreferences;

    /**
     * Este metodo configura Retrofit para realizar solicitudes HTTP
     * y añade un interceptor para incluir el token JWT en las cabeceras de cada solicitud.
     */
    public static InmobiliariaService getInmobiliariaService(Context context) {
        // Si la instancia de inmobiliariaService ya fue creada, la retorna directamente
        if (inmobiliariaService == null) {
            // Inicializa SharedPreferences si aún no está inicializado
            sharedPreferences = context.getSharedPreferences("token_prefs", Context.MODE_PRIVATE);
            // Construimos un cliente HTTP utilizando OkHttpClient para manejar las solicitudes
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

            // Si vamos a trabajar con https, la rutina para aceptar certs. autofirmados es:
            /*TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                    }

                    @Override
                    public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                    }

                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new java.security.cert.X509Certificate[]{};
                    }
                }
            };

            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0])
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, javax.net.ssl.SSLSession session) {
                        // Acepta cualquier nombre de host
                        return true;
                    }
                });*/

            // Añadimos un interceptor para manejar las cabeceras de autorización
            httpClient.addInterceptor(chain -> {
                // Captura la solicitud original
                Request originalRequest = chain.request();
                Request.Builder requestBuilder = originalRequest.newBuilder();

                // Lee el token guardado en SharedPreferences
                String token = sharedPreferences.getString("token", null);

                // Si el token no es nulo, lo añade en la cabecera "Authorization"
                if (token != null) {
                    requestBuilder.header("Authorization", "Bearer " + token);
                }

                // Construye la nueva solicitud con el token (si está presente)
                Request request = requestBuilder.build();

                // Envía la solicitud modificada con el token de autorización
                return chain.proceed(request);
            });

            // Crear un interceptor para el logging
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(loggingInterceptor);

            // Configuramos Retrofit con la URL base y añadimos GsonConverter para manejar JSON
            Gson gson = new GsonBuilder()
                    .setLenient()// Esto hace que el parseo sea más flexible
                    .registerTypeAdapter(Date.class, new DateSerializer())
                    .registerTypeAdapter(Date.class, new DateDeserializer())
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL) // La URL base de la API
                    .client(httpClient.build()) // El cliente HTTP configurado
                    .addConverterFactory(GsonConverterFactory.create(gson)) // Conversor JSON
                    .build();

            // Creación de la implementación de la interfaz InmobiliariaService
            inmobiliariaService = retrofit.create(InmobiliariaService.class);
        }

        // Retorna la instancia de InmobiliariaService
        return inmobiliariaService;
    }

    /**
     * Guarda el token JWT en SharedPreferences.
     */
    public static void guardarToken(Context context, String token) {
        if (sharedPreferences == null) {
            // Si SharedPreferences aún no está inicializado, lo inicializa
            sharedPreferences = context.getSharedPreferences("token_prefs", Context.MODE_PRIVATE);
        }
        // Guarda el token en SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", token);
        editor.apply(); // Aplica los cambios de forma asíncrona
    }

    /**
     * Elimina el token JWT de SharedPreferences.
     */
    public static void eliminarToken(Context context) {
        if (sharedPreferences == null) {
            // Inicializa SharedPreferences si no lo está
            sharedPreferences = context.getSharedPreferences("token_prefs", Context.MODE_PRIVATE);
        }
        // Elimina el token de SharedPreferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("token");
        //editor.apply(); // Aplica los cambios de forma asíncrona
        editor.commit(); // Aplica los cambios de forma síncrona
    }

    // Interfaz InmobiliariaService que define los endpoints de la API.
    public interface InmobiliariaService {

        // Login y recuperación de contraseña
        @POST("propietarios/login")
        @FormUrlEncoded
        Call<String> login(@Field("Usuario") String email, @Field("Clave") String pass);

        @Multipart
        @POST("propietarios/login")
            // no lo estoy usando. fue prueba de multipart
        Call<String> login2(
                @Part("Usuario") RequestBody email,
                @Part("Clave") RequestBody pass
        );

        @POST("propietarios/enviartokenrecuperacion")
        @FormUrlEncoded
        Call<Void> enviarTokenRecuperacion(@Field("email") String email);

        @POST("propietarios/cambiarclavecontoken")
        @FormUrlEncoded
        Call<Void> cambiarClaveConToken(
                @Header("Authorization") String bearerToken,
                @Field("nuevaClave") String pass);

        // CRUD para Propietarios
        @GET("propietarios")
        Call<List<Propietario>> getPropietarios();

        @GET("propietarios/getpropietario")
        Call<Propietario> getPropietario();

        @GET("propietarios/getpropietario/{id}")
        Call<Propietario> getPropietario(@Path("id") int id);

        @POST("propietarios")
        Call<Propietario> crearPropietario(@Body Propietario propietario);

        // El que va a utilizar el propietario logueado en la app
        @PUT("propietarios/actualizarpropietario")
        Call<Propietario> actualizarPropietario(@Body Propietario propietario);

        // El que va a utilizar el propietario logueado en la app
        @FormUrlEncoded
        @PUT("propietarios/actualizarclave")
        Call<Void> actualizarClave(
                @Field("currentpass") String currentPass,
                @Field("newpass") String newPass
        );

        @PUT("propietarios/{id}")
        Call<Propietario> actualizarPropietario(@Path("id") int id, @Body Propietario propietario);

        @DELETE("propietarios/{id}")
        Call<Void> eliminarPropietario(@Path("id") int id);

        // CRUD para Inmuebles
        @GET("inmuebles")
        Call<List<Inmueble>> getInmuebles();

        @GET("inmuebles/getinmueblesdepropietario")
        Call<List<Inmueble>> getInmueblesByPropietario();

        @GET("inmuebles/getDePropietarioSusInmueblesConContratos")
        Call<List<Inmueble>> getInmueblesWithContractsByPropietario();

        @GET("inmuebles/{id}")
        Call<Inmueble> getInmueble(@Path("id") int id);

        @Multipart
        @POST("inmuebles/crearinmueble")
        Call<Inmueble> crearInmueble(
                @Part("tiponombre") RequestBody tipo,
                @Part("usonombre") RequestBody uso,
                @Part("direccion") RequestBody direccion,
                @Part("precio") RequestBody precio,
                @Part("ambientes") RequestBody ambientes,
                @Part MultipartBody.Part image // Este es el archivo de imagen
        );


        @PUT("inmuebles/{id}")
        Call<Inmueble> actualizarInmueble(@Path("id") int id, @Body Inmueble inmueble);

        @PUT("inmuebles/actualizarDisponibilidadInmueble")
        Call<Void> actualizarDisponibilidadInmueble(@Body Inmueble inmueble);

        @DELETE("inmuebles/{id}")
        Call<Void> eliminarInmueble(@Path("id") int id);

        // CRUD para Inquilinos
        @GET("inquilinos")
        Call<List<Inquilino>> getInquilinos();

        @GET("inquilinos/{id}")
        Call<Inquilino> getInquilino(@Path("id") int id);

        @GET("inquilinos/getinquilinodeinmueble/{id}")
        Call<Inquilino> getInquilinoDeInmueble(@Path("id") int id);

        @POST("inquilinos")
        Call<Inquilino> crearInquilino(@Body Inquilino inquilino);

        @PUT("inquilinos/{id}")
        Call<Inquilino> actualizarInquilino(@Path("id") int id, @Body Inquilino inquilino);

        @DELETE("inquilinos/{id}")
        Call<Void> eliminarInquilino(@Path("id") int id);

        // CRUD para Contratos
        @GET("contratos")
        Call<List<Contrato>> getContratos();

        @GET("contratos/{id}")
        Call<Contrato> getContrato(@Path("id") int id);

        //obtenerContratoVigente
        @GET("contratos/getcontratoactualdeinmueble/{id}")
        Call<Contrato> getContratoActualDeInmueble(@Path("id") int id);

        @POST("contratos")
        Call<Contrato> crearContrato(@Body Contrato contrato);

        @PUT("contratos/{id}")
        Call<Contrato> actualizarContrato(@Path("id") int id, @Body Contrato contrato);

        @DELETE("contratos/{id}")
        Call<Void> eliminarContrato(@Path("id") int id);

        // CRUD para Pagos
        @GET("pagos")
        Call<List<Pago>> getPagos();

        @GET("pagos/getpagosdecontrato/{id}")
        Call<List<Pago>> getPagosDeContrato(@Path("id") int id);

        @GET("pagos/{id}")
        Call<Pago> getPago(@Path("id") int id);

        @POST("pagos")
        Call<Pago> crearPago(@Body Pago pago);

        @PUT("pagos/{id}")
        Call<Pago> actualizarPago(@Path("id") int id, @Body Pago pago);

        @DELETE("pagos/{id}")
        Call<Void> eliminarPago(@Path("id") int id);
    }

    public static String getHost() {
        return HOST + ":" + PORT + "/";
    }
}
