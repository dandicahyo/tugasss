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
import id.putraprima.retrofit.api.models.ProfileRequest;
import id.putraprima.retrofit.api.models.ProfileResponse;
import id.putraprima.retrofit.api.services.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateProfile extends AppCompatActivity {
    EditText edtUsername, edtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        edtUsername = findViewById(R.id.edtUsername);
        edtEmail = findViewById(R.id.edtEmail);
    }

    public static String token;
    public static final String SHARED_PREFS = "sharedPrefs";
    public void handleUpdate(View view) {
        String name = edtUsername.getText().toString();
        String email = edtEmail.getText().toString();
        SharedPreferences preferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        ProfileRequest pr = new ProfileRequest(email,name);
        ApiInterface service = ServiceGenerator.createService(ApiInterface.class);
        Call<Data<ProfileResponse>> call = service
                .doUpdProf("Bearer" + preferences.getString(token,""),pr);
        call.enqueue(new Callback<Data<ProfileResponse>>() {
            @Override
            public void onResponse(Call<Data<ProfileResponse>> call, Response<Data<ProfileResponse>> response) {
                Toast.makeText(UpdateProfile.this,"Berhasil mengubah profile ", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(UpdateProfile.this, ProfileActivity.class);
                startActivity(i);
            }

            @Override
            public void onFailure(Call<Data<ProfileResponse>> call, Throwable t) {
                Toast.makeText(UpdateProfile.this, "Gagal",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
