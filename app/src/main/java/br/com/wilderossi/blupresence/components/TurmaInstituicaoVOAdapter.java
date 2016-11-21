package br.com.wilderossi.blupresence.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.wilderossi.blupresence.R;
import br.com.wilderossi.blupresence.vo.TurmaInstituicaoVO;

public class TurmaInstituicaoVOAdapter extends ArrayAdapter<TurmaInstituicaoVO> {

    private int layoutResource;

    public TurmaInstituicaoVOAdapter(Context context, int layoutResource, List<TurmaInstituicaoVO> objects) {
        super(context, layoutResource, objects);
        this.layoutResource = layoutResource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            view = layoutInflater.inflate(layoutResource, null);
        }

        TurmaInstituicaoVO turmaInstituicaoVO = getItem(position);

        TextView txtNomeTurma = (TextView) view.findViewById(R.id.txtNomeTurma);
        TextView txtNomeInstituicao = (TextView) view.findViewById(R.id.txtNomeInstituicao);

        txtNomeTurma.setText(turmaInstituicaoVO.getTurma().getDescricao());
        txtNomeInstituicao.setText(turmaInstituicaoVO.getInstituicao().getNome());


        return view;
    }
}