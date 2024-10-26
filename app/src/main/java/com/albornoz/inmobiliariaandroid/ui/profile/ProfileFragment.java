package com.albornoz.inmobiliariaandroid.ui.profile;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.albornoz.inmobiliariaandroid.R;
import com.albornoz.inmobiliariaandroid.databinding.FragmentProfileBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ProfileFragment extends Fragment {

    private ProfileViewModel mViewModel;
    private FragmentProfileBinding binding;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(ProfileViewModel.class);
        binding = FragmentProfileBinding.inflate(inflater, container, false);

        initialize();

        View root = binding.getRoot();
        return root;
    }

    private void initialize() {
        mViewModel.getCurrentUser().observe(getViewLifecycleOwner(), p -> {
            binding.editTextEmailAddress.setText(p.getEmail());
            binding.editTextName.setText(p.getNombre());
            binding.editTextLastName.setText(p.getApellido());
            binding.editTextDni.setText(p.getDni());
            binding.editTextTel.setText(p.getTelefono());
            binding.editTextAddress.setText(p.getDireccion());

            // Aquí establecemos la fecha de nacimiento en el campo
            if (p.getFechaNacimiento() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                binding.editTextBirthDate.setText(sdf.format(p.getFechaNacimiento()));
            }
        });

        mViewModel.getButtonEditVisibility().observe(getViewLifecycleOwner(), visibility -> binding.buttonEdit.setVisibility(visibility));
        mViewModel.getButtonSaveVisibility().observe(getViewLifecycleOwner(), visibility -> binding.buttonSave.setVisibility(visibility));
        mViewModel.getButtonDateVisibility().observe(getViewLifecycleOwner(), visibility -> binding.buttonSelectBirthDate.setVisibility(visibility));

        mViewModel.getEditEnabled().observe(getViewLifecycleOwner(), flag -> {
            binding.editTextEmailAddress.setEnabled(flag);
            binding.editTextName.setEnabled(flag);
            binding.editTextLastName.setEnabled(flag);
            binding.editTextDni.setEnabled(flag);
            binding.editTextTel.setEnabled(flag);
            binding.editTextAddress.setEnabled(flag);
            binding.editTextBirthDate.setEnabled(flag); // Habilitar campo de fecha
        });

        mViewModel.getMsgVisibility().observe(getViewLifecycleOwner(), visibility -> binding.textViewMsg.setVisibility(visibility));
        mViewModel.getMsgMutableLiveData().observe(getViewLifecycleOwner(), s -> binding.textViewMsg.setText(s));

        binding.buttonEdit.setOnClickListener(view -> mViewModel.enableEdit());

        binding.buttonSave.setOnClickListener(view -> {
            String name = binding.editTextName.getText().toString();
            String lastName = binding.editTextLastName.getText().toString();
            String email = binding.editTextEmailAddress.getText().toString();
            String dni = binding.editTextDni.getText().toString();
            String tel = binding.editTextTel.getText().toString();
            String address = binding.editTextAddress.getText().toString();
            String birthDate = binding.editTextBirthDate.getText().toString();

            mViewModel.saveChanges2(name, lastName, dni, tel, email, address, birthDate);
        });

        // Manejar el evento de clic del botón para seleccionar la fecha
        binding.buttonSelectBirthDate.setOnClickListener(view -> showDatePickerDialog());
        binding.buttonChangePassword.setOnClickListener(v -> {
            // TODO: Navegar al fragmento o activity de cambio de clave
            NavController navController = NavHostFragment.findNavController(this);
            navController.navigate(R.id.action_nav_profile_to_passFragment);
        });

        mViewModel.setCurrentUser();
    }

    private void showDatePickerDialog() {
        // Obtener la fecha actual
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Crear el DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Formato de la fecha
                    String selectedDate = String.format(Locale.getDefault(), "%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear);
                    binding.editTextBirthDate.setText(selectedDate);
                }, year, month, day);

        datePickerDialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
