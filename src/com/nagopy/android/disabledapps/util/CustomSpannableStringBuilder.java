package com.nagopy.android.disabledapps.util;

import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;

import com.nagopy.android.disabledapps.R;

public class CustomSpannableStringBuilder {

	// private SparseArray<TextAppearanceSpan> mTarray;

	private TextAppearanceSpan mTextAppearanceSpanProcess;
	private TextAppearanceSpan mTextAppearanceSpanComment;

	public CustomSpannableStringBuilder(Context context) {
		// mTarray = new SparseArray<TextAppearanceSpan>();
		// mTarray.append(RunningAppProcessInfo.IMPORTANCE_BACKGROUND, new
		// TextAppearanceSpan(mContext,
		// R.style.ProcessStatus_Background));
		// mTarray.append(RunningAppProcessInfo.IMPORTANCE_EMPTY, new TextAppearanceSpan(mContext,
		// R.style.ProcessStatus_Empty));
		// mTarray.append(RunningAppProcessInfo.IMPORTANCE_FOREGROUND, new
		// TextAppearanceSpan(mContext,
		// R.style.ProcessStatus_Foreground));
		// mTarray.append(RunningAppProcessInfo.IMPORTANCE_PERCEPTIBLE, new
		// TextAppearanceSpan(mContext,
		// R.style.ProcessStatus_Perceptible));
		// mTarray.append(RunningAppProcessInfo.IMPORTANCE_SERVICE, new TextAppearanceSpan(mContext,
		// R.style.ProcessStatus_Service));
		// mTarray.append(RunningAppProcessInfo.IMPORTANCE_VISIBLE, new TextAppearanceSpan(mContext,
		// R.style.ProcessStatus_Visible));
		mTextAppearanceSpanProcess = new TextAppearanceSpan(context, R.style.ProcessStatus);
		mTextAppearanceSpanComment = new TextAppearanceSpan(context, R.style.Comment);
	}

	public CharSequence getLabelText(String packageName, String comment, int status) {
		String statusText = getStatusText(status);
		if (statusText == null && comment == null) {
			return packageName;
		}

		SpannableStringBuilder mBuilder = new SpannableStringBuilder();
		if (comment != null) {
			int start = mBuilder.length();
			mBuilder.append(comment);
			int end = mBuilder.length();
			mBuilder.setSpan(mTextAppearanceSpanComment, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
		if (statusText != null) {
			if (mBuilder.length() > 0) {
				mBuilder.append("\n");
			}
			int start = mBuilder.length();
			mBuilder.append(statusText);
			int end = mBuilder.length();
			mBuilder.setSpan(mTextAppearanceSpanProcess, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
		mBuilder.append("\n");
		mBuilder.append(packageName);

		return mBuilder;
	}

	private String getStatusText(int status) {
		String packageStatusText;
		switch (status) {
		case AppStatus.NULL_STATUS:
			packageStatusText = null;
			break;
		case RunningAppProcessInfo.IMPORTANCE_BACKGROUND:
			packageStatusText = "Background";
			break;
		case RunningAppProcessInfo.IMPORTANCE_FOREGROUND:
			packageStatusText = "Foreground";
			break;
		case RunningAppProcessInfo.IMPORTANCE_PERCEPTIBLE:
			packageStatusText = "Perceptible";
			break;
		case RunningAppProcessInfo.IMPORTANCE_SERVICE:
			packageStatusText = "Service";
			break;
		case RunningAppProcessInfo.IMPORTANCE_VISIBLE:
			packageStatusText = "Visible";
			break;
		case RunningAppProcessInfo.IMPORTANCE_EMPTY:
			packageStatusText = "Empty";
			break;
		default:
			packageStatusText = null;
			break;
		}
		return packageStatusText;
	}
}
