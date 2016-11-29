package br.com.wilderossi.blupresence.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.springframework.util.StringUtils;

import java.util.List;

import br.com.wilderossi.blupresence.ChamadaListActivity;
import br.com.wilderossi.blupresence.R;
import br.com.wilderossi.blupresence.transaction.Chamada;

public class SubtitledArrayAdapter extends ArrayAdapter<SubtitledAdapter> {

    private int layoutResource;
    private static final String SUBTITLE_FORMAT = " - %s";

    public SubtitledArrayAdapter(Context context, int layoutResource, List<? extends SubtitledAdapter> objects) {
        super(context, layoutResource, (List<SubtitledAdapter>) objects);
        this.layoutResource = layoutResource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            view = layoutInflater.inflate(layoutResource, null);
        }

        SubtitledAdapter subtitleAdapter = getItem(position);

        TextView txtMainString = (TextView) view.findViewById(R.id.txtMainString);
        TextView txtSubtitle = (TextView) view.findViewById(R.id.txtSubtitle);

        txtMainString.setText(subtitleAdapter.getMainString());
        String subtitle = subtitleAdapter.getSubtitle();
        if (subtitle != null && !subtitle.isEmpty()){
            txtSubtitle.setText(String.format(SUBTITLE_FORMAT, subtitle));
        } else {
            txtSubtitle.setText("");
        }

        return view;
    }
}