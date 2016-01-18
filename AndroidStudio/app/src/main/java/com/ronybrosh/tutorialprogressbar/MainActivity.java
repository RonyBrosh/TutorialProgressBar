package com.ronybrosh.tutorialprogressbar;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDemo1();
        initDemo2();
        initDemo3();
        initDemo4();
        initDemo5();
    }

    private void initDemo1()
    {
        final TutorialProgressBar tutorialProgressBar = (TutorialProgressBar) findViewById(R.id.TutorialProgressBar_1);
        tutorialProgressBar.useDefaultMask(true);

        final TextView textView = (TextView) findViewById(R.id.StepLabel_1);

        findViewById(R.id.Button_1).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int nextStep = tutorialProgressBar.getCurrentStep() + 1;
                tutorialProgressBar.setCurrentStep(nextStep, true);
                textView.setText(String.valueOf(tutorialProgressBar.getCurrentStep()));
            }
        });
    }

    private void initDemo2()
    {
        final TutorialProgressBar tutorialProgressBar = (TutorialProgressBar) findViewById(R.id.TutorialProgressBar_2);
        tutorialProgressBar.setStepsNumber(5);
        tutorialProgressBar.setStepsFillPercentage(new int[]{10, 30, 5, 20, 35});

        final TextView textView = (TextView) findViewById(R.id.StepLabel_2);

        findViewById(R.id.Button_2).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int nextStep = tutorialProgressBar.getCurrentStep() + 1;
                tutorialProgressBar.setCurrentStep(nextStep, true);
                textView.setText(String.valueOf(tutorialProgressBar.getCurrentStep()));
            }
        });
    }

    private void initDemo3()
    {
        final TutorialProgressBar tutorialProgressBar = (TutorialProgressBar) findViewById(R.id.TutorialProgressBar_3);
        tutorialProgressBar.useDefaultMask(true);
        tutorialProgressBar.setStepsNumber(6);
        tutorialProgressBar.setEmptyStepColors(new int[]{Color.parseColor("#1f2a55"), Color.parseColor("#451f55"), Color.parseColor("#1f552d"), Color.parseColor("#55541f"), Color.parseColor("#552a1f"), Color.parseColor("#4f1f55")});
        tutorialProgressBar.setFillStepColors(new int[]{Color.parseColor("#2541b0"), Color.parseColor("#761f9a"), Color.parseColor("#25c14d"), Color.parseColor("#aaa71d"), Color.parseColor("#b33e20"), Color.parseColor("#a955b3")});

        final TextView textView = (TextView) findViewById(R.id.StepLabel_3);

        findViewById(R.id.Button_3).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int nextStep = tutorialProgressBar.getCurrentStep() + 1;
                tutorialProgressBar.setCurrentStep(nextStep, true);
                textView.setText(String.valueOf(tutorialProgressBar.getCurrentStep()));
            }
        });
    }

    private void initDemo4()
    {
        final TutorialProgressBar tutorialProgressBar = (TutorialProgressBar) findViewById(R.id.TutorialProgressBar_4);
        tutorialProgressBar.setMask(BitmapFactory.decodeResource(getResources(), R.drawable.custom_mask));
        tutorialProgressBar.setStepsNumber(5);
        tutorialProgressBar.setFillStepColors(new int[]{Color.parseColor("#2541b0"), Color.parseColor("#761f9a"), Color.parseColor("#25c14d"), Color.parseColor("#aaa71d"), Color.parseColor("#b33e20")});

        final TextView textView = (TextView) findViewById(R.id.StepLabel_4);

        findViewById(R.id.Button_4).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int nextStep = tutorialProgressBar.getCurrentStep() + 1;
                tutorialProgressBar.setCurrentStep(nextStep, true);
                textView.setText(String.valueOf(tutorialProgressBar.getCurrentStep()));
            }
        });
    }

    private void initDemo5()
    {
        final TutorialProgressBar tutorialProgressBar = (TutorialProgressBar) findViewById(R.id.TutorialProgressBar_5);
        tutorialProgressBar.setMask(BitmapFactory.decodeResource(getResources(), R.drawable.custom_mask_2));
        tutorialProgressBar.setStepsNumber(3);
        tutorialProgressBar.setEmptyStepColors(new int[]{Color.parseColor("#9a8f4f"), Color.parseColor("#c5b974"), Color.parseColor("#9a8f4f")});
        tutorialProgressBar.setFillStepColors(new int[]{Color.parseColor("#e8c60c"), Color.parseColor("#e8c60c"), Color.parseColor("#e8c60c")});

        final TextView textView = (TextView) findViewById(R.id.StepLabel_5);

        findViewById(R.id.Button_5).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int nextStep = tutorialProgressBar.getCurrentStep() + 1;
                tutorialProgressBar.setCurrentStep(nextStep, true);
                textView.setText(String.valueOf(tutorialProgressBar.getCurrentStep()));
            }
        });
    }
}
