package com.bkjk.infra.test.bankcard;

import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Author: zhouzhenhua
 * Date: 2018/10/18
 * Version: 1.0.0
 * Description:
 */
public class EditTextWatcher implements TextWatcher {

    // default max length = 20 + 4 space
    private static final int DEFAULT_MAX_LENGTH = 20 + 4;
    // 最大可输入长度
    private int maxLength = DEFAULT_MAX_LENGTH;
    private int beforeTextLength = 0;
    private boolean isChanged = false;

    // 空格数量
    private int space = 0;

    private StringBuffer buffer = new StringBuffer();
    private EditText editText;

    public static void bind(EditText editText) {
        new EditTextWatcher(editText, DEFAULT_MAX_LENGTH);
    }

    public static void bind(EditText editText, int maxLength) {
        new EditTextWatcher(editText, maxLength);
    }

    public EditTextWatcher(EditText editText, int maxLength) {
        this.editText = editText;
        this.maxLength = maxLength;
        editText.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        beforeTextLength = s.length();
        if (buffer.length() > 0) {
            buffer.delete(0, buffer.length());
        }
        space = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ' ') {
                space ++;
            }
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        int length = s.length();
        buffer.append(s.toString());
        if (length == beforeTextLength || length <= 3
                || isChanged) {
            isChanged = false;
            return;
        }
        isChanged = true;
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (isChanged) {
            int selectionIndex = editText.getSelectionEnd();
            // total char length
            int index = 0;
            while (index < buffer.length()) {
                if (buffer.charAt(index) == ' ') {
                    buffer.deleteCharAt(index);
                } else {
                    index ++;
                }
            }
            // total space count
            index = 0;
            int totalSpace = 0;
            while (index < buffer.length()) {
                if ((index == 4 || index == 9 || index == 14 || index == 19 || index == 24)) {
                    buffer.insert(index, ' ');
                    totalSpace ++;
                }
                index++;
            }
            // selection index
            if (totalSpace > space) {
                selectionIndex += (totalSpace - space);
            }
            char[] tempChar = new char[buffer.length()];
            buffer.getChars(0, buffer.length(), tempChar, 0);
            String str = buffer.toString();
            if (selectionIndex > str.length()) {
                selectionIndex = str.length();
            } else if (selectionIndex < 0) {
                selectionIndex = 0;
            }
            editText.setText(str);
            Editable text = editText.getText();
            // set selection
            Selection.setSelection(text, selectionIndex < maxLength ? selectionIndex : maxLength);
            isChanged = false;
        }
    }
}