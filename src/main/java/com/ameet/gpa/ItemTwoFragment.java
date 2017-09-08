/**
 * Created by @ameetv on 3/25/2017.
 */

package com.ameet.gpa;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

public class ItemTwoFragment extends Fragment implements View.OnClickListener {
    TextView desired_gpa;
    Button calculate, reset;
    EditText curr_gpa, curr_credits, goal_gpa, goal_credits;
    LayoutInflater inflater_G;

    public static ItemTwoFragment newInstance() {
        ItemTwoFragment fragment = new ItemTwoFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_two, container, false);

        inflater_G = LayoutInflater.from(getActivity().getApplicationContext());

        curr_gpa = (EditText) view.findViewById(R.id.curr_gpa);
        curr_credits = (EditText) view.findViewById(R.id.curr_credt);
        goal_gpa = (EditText) view.findViewById(R.id.goal_gpa);
        goal_credits = (EditText) view.findViewById(R.id.goal_credt);

        desired_gpa = (TextView) view.findViewById(R.id.desired_gpa);

        calculate = (Button) view.findViewById(R.id.calculate1);
        reset = (Button) view.findViewById(R.id.reset1);

        calculate.setOnClickListener(this);
        reset.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.calculate1:
                boolean flagFail=false;
                double currgpa = 0,
                        currcredit = 0,
                        goalgpa = 0,
                        goalcredit = 0;

                if (TextUtils.isEmpty(curr_gpa.getText()) || !(Double.parseDouble(curr_gpa.getText().toString()) >= 0 && Double.parseDouble(curr_gpa.getText().toString()) <= 4)) {
                    curr_gpa.setError("Current GPA should be number between 0-4");
                    flagFail=true;
                }
                if (TextUtils.isEmpty(curr_credits.getText()) || !(Double.parseDouble(curr_credits.getText().toString()) >= 0 && Double.parseDouble(curr_credits.getText().toString()) <= 99) ) {
                    curr_credits.setError("Current credits should be number between 0-99");
                    flagFail=true;
                }
                if (TextUtils.isEmpty(goal_gpa.getText()) || !(Double.parseDouble(goal_gpa.getText().toString()) >= 0 && Double.parseDouble(goal_gpa.getText().toString()) <= 4)) {
                    goal_gpa.setError("Goal GPA should be number between 0-4");
                    flagFail=true;
                }
                if (TextUtils.isEmpty(goal_credits.getText()) || !(Double.parseDouble(goal_credits.getText().toString()) >= 0 && Double.parseDouble(goal_credits.getText().toString()) <= 99)) {
                    goal_credits.setError("Additional credits should be number between 0-99");
                    flagFail=true;
                }
                if(flagFail){
                    sendAlert("There are errors in the input. Please fix them and try again.");
                    break;
                }
                //get the data input
                currgpa = Double.parseDouble(curr_gpa.getText().toString());
                currcredit = Double.parseDouble(curr_credits.getText().toString());
                goalgpa = Double.parseDouble(goal_gpa.getText().toString());
                goalcredit = Double.parseDouble(goal_credits.getText().toString());

                double totalCredits = currcredit + goalcredit;
                double pointsEarned = currgpa * currcredit;
                double pointsNeeded = totalCredits * goalgpa;
                double pointsNeededThisSemester = pointsNeeded - pointsEarned;
                double gpaReqdThisSemester = roundNumber(pointsNeededThisSemester / currcredit);

                if (gpaReqdThisSemester < 0.0 || gpaReqdThisSemester > 4.0) {
                    double pointsEarnedThisSemester = 4.0 * currcredit;
                    double totalPoints = pointsEarned + pointsEarnedThisSemester;
                    double highestGpaPossible = roundNumber(totalPoints / totalCredits);
                    sendAlert("GPA "+gpaReqdThisSemester+" cannot be achieved this semester. The highest cumulative GPA you can achieve is " + highestGpaPossible + ".");
                }
                else{
                    desired_gpa.setText("To achieve a target of "+goalgpa+", Your goal GPA should be: " +(gpaReqdThisSemester));
                    sendAlert("To achieve a target of "+goalgpa+", Your goal GPA should be: " + (gpaReqdThisSemester));
                }

                break;
            case R.id.reset1:
                initDefaultValue();
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initDefaultValue();
    }

    public void initDefaultValue() {
        curr_gpa.setError(null);
        curr_credits.setError(null);
        goal_gpa.setError(null);
        goal_credits.setError(null);
        desired_gpa.setError(null);

        curr_credits.setText("");
        curr_gpa.setText("");
        goal_credits.setText("");
        goal_gpa.setText("");
        desired_gpa.setText("");
    }

    private void sendAlert(String msg){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setTitle("Alert");
        alertDialogBuilder
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private double roundNumber (double number){
        DecimalFormat df = new DecimalFormat("#.###");
        number = Double.valueOf(df.format(number));
        return number;
    }
}
