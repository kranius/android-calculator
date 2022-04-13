package com.kranius.tpcalculatrice;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private TextView txtResult;
    private Button btnOne, btnTwo, btnThree, btnFour, btnFive, btnSix, btnSeven, btnEight, btnNine;
    private Button btnZero, btnComma, btnClear, btnCompute, btnPlus, btnMinus, btnMultiply, btnDivide;
    private TextView txtRes1, txtRes2, txtResOp;

    private Boolean isOperationChosen = false;
    private Boolean isCommaChosen = false;
    private Boolean isLeadingZero = true;
    private Boolean status = true;
    private String firstParam = null, secondParam = null;
    private Double result;
    private MyOperation operation;

    private StringBuilder inputExpression = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtResult = findViewById(R.id.txtResult);
        btnOne = findViewById(R.id.btnOne);
        btnTwo = findViewById(R.id.btnTwo);
        btnThree = findViewById(R.id.btnThree);
        btnFour = findViewById(R.id.btnFour);
        btnFive = findViewById(R.id.btnFive);
        btnSix = findViewById(R.id.btnSix);
        btnSeven = findViewById(R.id.btnSeven);
        btnEight = findViewById(R.id.btnEight);
        btnNine = findViewById(R.id.btnNine);
        txtRes1 = findViewById(R.id.txtNbr1);
        txtRes2 = findViewById(R.id.txtNbr2);
        txtResOp = findViewById(R.id.txtOp);
    }

    public void btnOne(View view) {
        appendAndSetTxtResult('1');
    }

    public void btnTwo(View view) {
        appendAndSetTxtResult('2');
    }

    public void btnThree(View view) {
        appendAndSetTxtResult('3');
    }

    public void btnFour(View view) {
        appendAndSetTxtResult('4');
    }

    public void btnFive(View view) {
        appendAndSetTxtResult('5');
    }

    public void btnSix(View view) {
        appendAndSetTxtResult('6');
    }

    public void btnSeven(View view) {
        appendAndSetTxtResult('7');
    }

    public void btnEight(View view) {
        appendAndSetTxtResult('8');
    }

    public void btnNine(View view) {
        appendAndSetTxtResult('9');
    }

    public void btnZero(View view) {
        if (!isLeadingZero) {
            appendAndSetTxtResult('0');
        }
    }

    private void appendAndSetTxtResult(Character c) {
        inputExpression.append(c);
        txtResult.setText(inputExpression.toString());
        isLeadingZero = false;
    }

    public void btnComma(View view) {
        if (!isCommaChosen) {
            appendAndSetTxtResult('.');
            isCommaChosen = true;
        } else {
            Toast.makeText(this, "Double virgule impossible !", Toast.LENGTH_LONG).show();
            btnClear(view);
        }
    }

    private void setOperation(MyOperation op) {
        if (!isOperationChosen) {
            operation = op;
            isOperationChosen = true;
            firstParam = inputExpression.toString();
            txtRes1.setText(getString(R.string.txt_n1) + firstParam);
            txtResOp.setText(getString(R.string.txt_op) + operation.toString());
        } else {
            Toast.makeText(this, "Double operation impossible !", Toast.LENGTH_LONG).show();
        }
        clearContext();
    }

    public void btnPlus(View view) {
        setOperation(MyOperation.OP_ADD);
    }

    public void btnMinus(View view) {
        setOperation(MyOperation.OP_SUB);
    }

    public void btnMult(View view) {
        setOperation(MyOperation.OP_MUL);
    }

    public void btnDivide(View view) {
        setOperation(MyOperation.OP_DIV);
    }

    public void btnClear(View view) {
        clearContext();
        clearHistory();
    }

    private void clearHistory() {
        txtResOp.setText(getString(R.string.txt_op));
        txtRes1.setText(getString(R.string.txt_n1));
        txtRes2.setText(getString(R.string.txt_n2));
    }

    private void clearContext() {
        txtResult.setText("0");
        inputExpression.setLength(0);
        isOperationChosen = false;
        isCommaChosen = false;
        isLeadingZero = true;
    }

    public void btnCompute(View view) {
        if (!status || inputExpression.length() < 1) {
            Toast.makeText(this, "Error invalid operation", Toast.LENGTH_LONG).show();
            clearContext();
        } else {
            secondParam = inputExpression.toString();
            txtRes2.setText(getString(R.string.txt_n2) + secondParam);
            try {
                double n1 = Double.parseDouble(firstParam);
                double n2 = Double.parseDouble(secondParam);
                if (n2 == 0 && operation == MyOperation.OP_DIV) {
                    Toast.makeText(this, "cannot divide by zero", Toast.LENGTH_LONG).show();
                } else {
                    compute(n1, n2);
                    clearContext();
                    txtResult.setText(String.format(Locale.FRANCE, "%f", result));
                }
            } catch (Exception ex) {
                Toast.makeText(this, "Invalid number", Toast.LENGTH_LONG).show();
                Log.e(TAG, ex.toString());
                clearContext();
            }
        }
    }

    private void compute(Double n1, Double n2) {
        switch (operation) {
            case OP_ADD:
                result = n1 + n2;
                break;
            case OP_SUB:
                result = n1 - n2;
                break;
            case OP_DIV:
                result = n1 / n2;
                break;
            case OP_MUL:
                result = n1 * n2;
                break;
            default:
                break;
        }
    }
}