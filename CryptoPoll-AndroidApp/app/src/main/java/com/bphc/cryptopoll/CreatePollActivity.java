package com.bphc.cryptopoll;

import static com.bphc.cryptopoll.prefs.SharedPrefsConstants.JWTS_TOKEN;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Toast;

import com.bphc.cryptopoll.helper.APIClient;
import com.bphc.cryptopoll.helper.Webservices;
import com.bphc.cryptopoll.models.LoginResponse;
import com.bphc.cryptopoll.models.PollResponse;
import com.bphc.cryptopoll.prefs.SharedPrefs;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CreatePollActivity extends AppCompatActivity {

    private TextInputLayout textName, textDescription, textAddOption;
    private String name, description, option;
    private Button buttonStartDate, buttonEndDate, buttonAddOption, buttonCreatePoll;
    private ListView listOptions;
    private ArrayList<String> options;
    private int flag;

    private DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_poll);

        initDatePicker();
        options = new ArrayList<>();

        textName = findViewById(R.id.layout_add_poll_name);
        textDescription = findViewById(R.id.layout_add_poll_description);
        textAddOption = findViewById(R.id.layout_add_poll_option);

        buttonStartDate = findViewById(R.id.button_start_date);
        buttonEndDate = findViewById(R.id.button_end_date);
        initButtons();

        buttonAddOption = findViewById(R.id.button_add_options);
        buttonCreatePoll = findViewById(R.id.button_create_poll);

        listOptions = findViewById(R.id.list_options);
        ArrayAdapter<String> listAdapter = new ArrayAdapter<>(
                CreatePollActivity.this,
                android.R.layout.simple_list_item_1,
                options);
        listOptions.setAdapter(listAdapter);

        buttonStartDate.setOnClickListener(view -> {
            flag = 0;
            datePickerDialog.show();
        });

        buttonEndDate.setOnClickListener(view -> {
            flag = 1;
            datePickerDialog.show();
        });

        buttonAddOption.setOnClickListener(view -> {
            if(!validatePollOption()) return;
            options.add(option);
            textAddOption.getEditText().setText("");
            listAdapter.notifyDataSetChanged();
        });

        buttonCreatePoll.setOnClickListener(view -> {
            if (!validatePollName() | !validatePollDescription() | isEmptyOptions())
                return;
            createPoll();
            finish();
        });
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                if (flag == 0)
                    buttonStartDate.setText(year + "-" + month + "-" + day);
                else
                    buttonEndDate.setText(year + "-" + month + "-" + day);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    private void initButtons() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        buttonStartDate.setText(year + "-" + month + "-" + day);
        buttonEndDate.setText(year + "-" + month + "-" + (day + 1));
    }

    private void createPoll() {
        Retrofit retrofit = APIClient.getRetrofitInstance();
        Webservices webservices = retrofit.create(Webservices.class);

        HashMap<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("description", description);
        map.put("start_date", buttonStartDate.getText().toString() + " 5:00:00");
        map.put("end_date", buttonEndDate.getText().toString() + " 5:00:00");
        map.put("choices", options);

        Call<PollResponse> call = webservices
                .createPoll("Bearer " + SharedPrefs.getStringParams(this, JWTS_TOKEN, ""), map);

        call.enqueue(new Callback<PollResponse>() {
            @Override
            public void onResponse(Call<PollResponse> call, Response<PollResponse> response) {

            }

            @Override
            public void onFailure(Call<PollResponse> call, Throwable t) {

            }
        });
    }

    private boolean validatePollDescription() {
        description = textDescription.getEditText().getText().toString().trim();
        if (description.isEmpty()) {
            textDescription.setError("* Add the description");
            return false;
        } else {
            textDescription.setError(null);
            return true;
        }
    }

    private boolean validatePollName() {
        name = textName.getEditText().getText().toString().trim();
        if (name.isEmpty()) {
            textName.setError("* Add the name");
            return false;
        } else {
            textName.setError(null);
            return true;
        }
    }

    private boolean validatePollOption() {
        option = textAddOption.getEditText().getText().toString().trim();
        if (option.isEmpty()) {
            textAddOption.setError("* Write the option");
            return false;
        } else {
            textAddOption.setError(null);
            return true;
        }
    }

    private boolean isEmptyOptions() {
        if (options.isEmpty()) {
            Toast.makeText(this, "Add atleast one option", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }
}