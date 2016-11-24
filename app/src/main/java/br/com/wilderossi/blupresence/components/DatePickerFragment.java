package br.com.wilderossi.blupresence.components;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

import br.com.wilderossi.blupresence.util.DateUtils;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private final Calendar calendar;
    private final EditText dateField;

    public DatePickerFragment(Calendar calendar, EditText dateField) {
        this.calendar = calendar;
        this.dateField = dateField;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);

        dateField.setText(DateUtils.getDateString(calendar));
    }
}