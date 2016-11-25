package br.com.wilderossi.blupresence.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

import br.com.wilderossi.blupresence.R;
import br.com.wilderossi.blupresence.vo.AlunoPresencaVO;
import br.com.wilderossi.blupresence.vo.ChamadaVO;
import br.com.wilderossi.blupresence.vo.TurmaInstituicaoVO;

public class ChamadaAlunoAdapter extends ArrayAdapter<AlunoPresencaVO> {

    private int layoutResource;

    public ChamadaAlunoAdapter(Context context, int layoutResource, List<AlunoPresencaVO> objects) {
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

        final AlunoPresencaVO alunoPresencaVO = getItem(position);

        TextView txtNomeAlunoChamada = (TextView) view.findViewById(R.id.txtNomeAlunoChamada);
        CheckBox checkBoxAlunoPresente = (CheckBox) view.findViewById(R.id.checkBoxAlunoPresente);

        txtNomeAlunoChamada.setText(alunoPresencaVO.getAluno().getNome());
        checkBoxAlunoPresente.setChecked(alunoPresencaVO.getPresente());
        checkBoxAlunoPresente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alunoPresencaVO.setPresente(!alunoPresencaVO.getPresente());
            }
        });

        return view;
    }
}