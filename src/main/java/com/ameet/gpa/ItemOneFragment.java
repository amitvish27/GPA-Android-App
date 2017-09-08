/**
 * Created by @ameetv on 3/25/2017.
 */

package com.ameet.gpa;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import models.GPAClassModel;
import models.GradeModel;

public class ItemOneFragment extends Fragment implements View.OnClickListener {

    public static ArrayList<GradeModel> gradeValueList = new ArrayList<>();
    public static ArrayList<GradeModel> previousGradeValueList = new ArrayList<>();

    ArrayList<GPAClassModel> list = null;

    TextView total_gpa, total_grade_points, total_units;
    Button calculate, reset;
    Button add;
    LinearLayout grade_layout;
    EditText prev_credits, prev_gpa;
    LayoutInflater inflater_G;


    public static ItemOneFragment newInstance() {
        ItemOneFragment fragment = new ItemOneFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_one, container, false);

        inflater_G = LayoutInflater.from(getActivity());

        total_gpa = (TextView) view.findViewById(R.id.total_gpa);
        total_units = (TextView) view.findViewById(R.id.total_units);
        total_grade_points = (TextView) view.findViewById(R.id.total_grade_points);

        prev_credits = (EditText) view.findViewById(R.id.prev_credits);
        prev_gpa = (EditText) view.findViewById(R.id.prev_gpa);

        grade_layout = (LinearLayout) view.findViewById(R.id.grade_layout);

        add = (Button) view.findViewById(R.id.add);
        calculate = (Button) view.findViewById(R.id.calculate);
        reset = (Button) view.findViewById(R.id.reset);

        add.setOnClickListener(this);
        calculate.setOnClickListener(this);
        reset.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.add:
                View additionView = inflater_G.inflate(R.layout.layout_grade, null, false);
                TextView course = (TextView) additionView.findViewById(R.id.course);
                EditText units = (EditText) additionView.findViewById(R.id.units);
                Spinner repeat_grade = (Spinner) additionView.findViewById(R.id.repeat_grade);
                Spinner grade = (Spinner) additionView.findViewById(R.id.grade);
                units.setId(1000 + list.size());
                repeat_grade.setId(3000 + list.size());
                grade.setId(4000 + list.size());
                course.setText("Course " + (list.size() + 1));
                units.setText("");
                list.add(new GPAClassModel("Course " + (list.size() + 1), 0, 0, "", 0, "", 0, 0));
                grade_layout.addView(additionView);
                break;
            case R.id.calculate:
                double totalGradePoints = 0,
                        totalUnits = 0,
                        previousGradePoint = 0,
                        repeatGradePoint = 0,
                        repeatTotalUnits = 0;
                total_gpa.setText("");
                total_gpa.setError(null);
                total_units.setText("");
                total_grade_points.setText("");
                boolean flagFail = false;

                if (!(!TextUtils.isEmpty(prev_gpa.getText()) && Double.parseDouble(prev_gpa.getText().toString()) >= 0 && Double.parseDouble(prev_gpa.getText().toString()) <= 4)) {
                    prev_gpa.setError("Enter Current GPA between 0-4");
                    flagFail = true;
                }

                if (!(!TextUtils.isEmpty(prev_credits.getText()) && Double.parseDouble(prev_credits.getText().toString()) >= 0 && Double.parseDouble(prev_credits.getText().toString()) <= 99)) { //limit to 99
                    prev_credits.setError("Enter Units attempted for letter grade, between 0-99.");
                    flagFail=true;
                }
                for (int i = 0; i < list.size(); i++) {

                    EditText c_units = (EditText) getView().findViewById(1000 + i);
                    Spinner c_repeat_grade = (Spinner) getView().findViewById(3000 + i);
                    Spinner c_grade = (Spinner) getView().findViewById(4000 + i);

                    c_units.setError(null);
                    ((TextView) c_repeat_grade.getSelectedView()).setError(null);
                    ((TextView) c_grade.getSelectedView()).setError(null);

                    if (TextUtils.isEmpty(c_units.getText())) {
                        c_units.setError("Enter Units");
                        flagFail = true;
                    }

                    if (c_grade.getSelectedItemPosition() == 0) {
                        ((TextView) c_grade.getSelectedView()).setError("Select Grade");
                        flagFail = true;
                    }

                    if (!flagFail) {

                        list.get(i).setUnits(!TextUtils.isEmpty(c_units.getText()) ? Double.parseDouble(c_units.getText().toString()) : 0);
                        if (c_grade.getSelectedItemPosition() > 0) {
                            list.get(i).setGrade(gradeValueList.get(c_grade.getSelectedItemPosition() - 1).getGrade());
                            list.get(i).setGradeValue(gradeValueList.get(c_grade.getSelectedItemPosition() - 1).getValue());
                            list.get(i).setGradePoints(list.get(i).getGradeValue() * list.get(i).getUnits());
                        } else {
                            list.get(i).setGrade("");
                            list.get(i).setGradeValue(0);
                            list.get(i).setGradePoints(0);
                        }

                        if (c_repeat_grade.getSelectedItemPosition() > 0) {
                            list.get(i).setPreviousGrade(previousGradeValueList.get(c_repeat_grade.getSelectedItemPosition() - 1).getGrade());
                            list.get(i).setPreviousGradeValue(previousGradeValueList.get(c_repeat_grade.getSelectedItemPosition() - 1).getValue());
                            repeatGradePoint += list.get(i).getPreviousGradeValue() * list.get(i).getUnits();
                            repeatTotalUnits += list.get(i).getUnits();
                        } else {
                            list.get(i).setPreviousGrade("");
                            list.get(i).setPreviousGradeValue(0);
                        }

                        Log.d(this.getClass().getSimpleName().toString(), "gPoints : " + list.get(i).getGradePoints() + " and units : " + list.get(i).getUnits());

                        totalGradePoints += list.get(i).getGradePoints();
                        totalUnits += list.get(i).getUnits();

                    }
                }
                if (flagFail) {
                    sendAlert("There are errors in the input. Please fix them and try again.");
                    break;
                }

                previousGradePoint = Double.parseDouble(prev_gpa.getText().toString()) * Double.parseDouble(prev_credits.getText().toString());
                previousGradePoint -= repeatGradePoint;
                totalGradePoints += previousGradePoint;
                totalUnits += Double.parseDouble(prev_credits.getText().toString()) - repeatTotalUnits;

                if (repeatTotalUnits > Double.parseDouble(prev_credits.getText().toString())) {
                    prev_credits.setError("Units Attempted should be greater than total repeat units.");
                    sendAlert("Units Attempted should be greater than total repeat units.");
                    break;
                }
                double totalGPA = roundNumber(totalGradePoints / totalUnits);
                if (totalGPA > 4.0 || totalGPA < 0.0) {
                    sendAlert("GPA " + totalGPA + " cannot be greater than 4.0. Please verify input values.");
                } else {
                    sendAlert("Your GPA is " + totalGPA + "" +
                            "\nTotal Units Attempted: " + roundNumber(totalUnits) +
                            "\nTotal Grade Points: " + roundNumber(totalGradePoints));
                    total_gpa.setText("GPA : " + totalGPA);
                    total_units.setText("Total Units Attempted: " + totalUnits);
                    total_grade_points.setText("Total Grade Points: " + totalGradePoints);
                }


                for (GPAClassModel m : list) {
                    Log.d(this.getClass().getSimpleName().toString(), m.toString());
                }

                break;
            case R.id.reset:
                prev_gpa.setError(null);
                prev_credits.setError(null);
                total_gpa.setError(null);
                initDefaultValue();
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initGradeValueList();
        initPreviousGradeValueList();
        initDefaultValue();
    }

    public void initDefaultValue() {
        grade_layout.removeAllViews();
        list = new ArrayList<>();

        prev_credits.setText("");
        prev_gpa.setText("");

        total_gpa.setText("");
        total_units.setText("");
        total_grade_points.setText("");

        for (int i = 0; i < 1; i++) {

            View additionView = inflater_G.inflate(R.layout.layout_grade, null, false);

            TextView course = (TextView) additionView.findViewById(R.id.course);
            EditText units = (EditText) additionView.findViewById(R.id.units);
            Spinner grade = (Spinner) additionView.findViewById(R.id.grade);
            Spinner repeat_grade = (Spinner) additionView.findViewById(R.id.repeat_grade);

            units.setId(1000 + list.size());
            repeat_grade.setId(3000 + list.size());
            grade.setId(4000 + list.size());

            course.setText("Course " + (i + 1));
            units.setText("");

            list.add(new GPAClassModel("Course " + (list.size() + 1), 0, 0, "", 0, "", 0, 0));

            grade_layout.addView(additionView);
        }
    }

    private void initGradeValueList() {
        gradeValueList.clear();
        gradeValueList.add(new GradeModel("A", 4.0));
        gradeValueList.add(new GradeModel("A-", 3.7));
        gradeValueList.add(new GradeModel("B+", 3.3));
        gradeValueList.add(new GradeModel("B", 3.0));
        gradeValueList.add(new GradeModel("B-", 2.7));
        gradeValueList.add(new GradeModel("C+", 2.3));
        gradeValueList.add(new GradeModel("C", 2.0));
        gradeValueList.add(new GradeModel("C-", 1.7));
        gradeValueList.add(new GradeModel("D+", 1.3));
        gradeValueList.add(new GradeModel("D", 1.0));
        gradeValueList.add(new GradeModel("D-", 0.7));
        gradeValueList.add(new GradeModel("F", 0.0));
        gradeValueList.add(new GradeModel("FN", 0.0));
    }

    private void initPreviousGradeValueList() {
        previousGradeValueList.clear();
        previousGradeValueList.add(new GradeModel("C-", 1.7));
        previousGradeValueList.add(new GradeModel("D+", 1.3));
        previousGradeValueList.add(new GradeModel("D", 1.0));
        previousGradeValueList.add(new GradeModel("D-", 0.7));
        previousGradeValueList.add(new GradeModel("F", 0.0));
        previousGradeValueList.add(new GradeModel("FN", 0.0));
    }

    private void sendAlert(String msg) {
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
