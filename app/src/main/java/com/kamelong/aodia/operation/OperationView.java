package com.kamelong.aodia.operation;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.kamelong.aodia.R;
import com.kamelong.aodia.diadata.AOdiaDiaFile;
import com.kamelong.aodia.diadata.AOdiaTrain;
import com.kamelong.aodia.diadata.Operation;
import com.kamelong.aodia.timeTable.TrainSelectListener;

/**
 */

public class OperationView extends RelativeLayout{
    View layout;
    boolean childOpen=true;
    Operation operation=null;
    AOdiaDiaFile diaFile=null;
    OperationFragment fragment=null;
    int fileID=0;
    int diaNum=0;
    LinearLayout opeList=null;

    public OperationView(final Context context, final OperationFragment fragment,final Operation operation, final AOdiaDiaFile diaFile, final int fileID, final int diaNum){
        super(context);
        this.fragment=fragment;
        this.diaFile=diaFile;
        this.fileID=fileID;
        this.diaNum=diaNum;
        this.operation=operation;
        layout = LayoutInflater.from(context).inflate(R.layout.operation_view, this);
        opeList=layout.findViewById(R.id.opeList);
        if(opeList.getChildCount()==0) {
            for (int i = 0; i < operation.getTrainNum(); i++) {
                opeList.addView(new OperationSubView(context, operation.getTrain(i)));
            }
        }

        layout.findViewById(R.id.edit).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("OK");
                childOpen=!childOpen;

            }
        });
        layout.findViewById(R.id.addbutton).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewTrip();
            }
        });
        layout.findViewById(R.id.addButton).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment.addNewOpeView(OperationView.this);
            }
        });
        layout.findViewById(R.id.deleteButton).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment.deleteOpeView(OperationView.this);
            }
        });

        final EditText nameEdit=layout.findViewById(R.id.opeNameEdit);
        final EditText numberEdit=layout.findViewById(R.id.opeNumberEdit);
        nameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2){}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                    operation.setName(nameEdit.getText().toString());
            }
        });
        numberEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2){}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                if (numberEdit.getText().toString().length() > 0) {
                    operation.setNumber(Integer.parseInt(numberEdit.getText().toString()));
                }
            }
        });
        final String k=operation.getName();
        nameEdit.setText(k,TextView.BufferType.EDITABLE);
        System.out.println(nameEdit);
        System.out.println(k);
        System.out.println(nameEdit.getText().toString());
        if(operation.getNumber()!=-1){
            final String s=operation.getNumber()+"";
            numberEdit.setText(s, TextView.BufferType.EDITABLE);
            System.out.println(numberEdit.getText().toString());
        }

    }

    public void addNewTrip(final OperationSubView subView){
        TrainSelectDialog dialog=new TrainSelectDialog(getContext(), diaFile, fileID, diaNum, new TrainSelectListener() {
            @Override
            public void selectTrain(AOdiaTrain train) {
                for (int i = 0; i < operation.getTrainNum(); i++) {
                    if(operation.getTrain(i)==subView.train){
                        operation.addTrain(i,train);
                        opeList.addView(new OperationSubView(getContext(),train),i);
                        break;
                    }
                }
                invalidate();

            }
        });
        dialog.show();
    }
    public void addNewTrip(){
        TrainSelectDialog dialog=new TrainSelectDialog(getContext(), diaFile, fileID, diaNum, new TrainSelectListener() {
            @Override
            public void selectTrain(AOdiaTrain train) {
                        operation.addTrain(train);
                        opeList.addView(new OperationSubView(getContext(),train));
                invalidate();

            }
        });
        dialog.show();
    }

    public void deleteTrip(final OperationSubView subView){
        operation.removeTrain(subView.train);
        opeList.removeView(subView);
        invalidate();
    }

}