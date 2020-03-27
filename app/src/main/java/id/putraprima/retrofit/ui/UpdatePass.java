package id.putraprima.retrofit.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import id.putraprima.retrofit.R;
import id.putraprima.retrofit.api.helper.ServiceGenerator;
import id.putraprima.retrofit.api.models.Data;
import id.putraprima.retrofit.api.models.PasswordRequest;
import id.putraprima.retrofit.api.models.ProfileResponse;
import id.putraprima.retrofit.api.services.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdatePass extends AppCompatActivity {
    EditText edtPassword, edtPasswordConfirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pass);
        edtPassword = findViewById(R.id.edtPassword);
        edtPasswordConfirm = findViewById(R.id.edtPasswordConfirm);
    }

    public static String token;
    public static final String SHARED_PREFS = "sharedPrefs";
    public void handleUpdate(View view) {
        String password = edtPassword.getText().toString();
        String password_confirmation = edtPasswordConfirm.getText().toString();
        SharedPreferences preferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        PasswordRequest pr = new PasswordRequest(password,password_confirmation);
        ApiInterface service = ServiceGenerator.createService(ApiInterface.class);
        Call<Data<ProfileResponse>> call = service
                .doUpdPass("Bearer" + preferences.getString(token,""),pr);
        call.enqueue(new Callback<Data<ProfileResponse>>() {
            @Override
            public void onResponse(Call<Data<ProfileResponse>> call, Response<Data<ProfileResponse>> response) {
                Toast.makeText(UpdatePass.this,"Berhasil mengubah profile ", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(UpdatePass.this, MainActivity.class);
                startActivity(i);
            }

            @Override
            public void onFailure(Call<Data<ProfileResponse>> call, Throwable t) {
                Toast.makeText(UpdatePass.this, "Gagal",Toast.LENGTH_SHORT).show();
            }
        });
    }
}