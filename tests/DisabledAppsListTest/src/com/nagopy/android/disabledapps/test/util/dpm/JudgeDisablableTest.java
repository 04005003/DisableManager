package com.nagopy.android.disabledapps.test.util.dpm;

import com.nagopy.android.disabledapps.util.dpm.JudgeDisablable;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.test.AndroidTestCase;

public class JudgeDisablableTest extends AndroidTestCase {

	private JudgeDisablable mJudgeDisablable;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		mJudgeDisablable = JudgeDisablable.getInstance(getContext());
	}

	public void testUserApp() {
		ApplicationInfo applicationInfo = getAppInfo(getContext().getPackageName());
		assertFalse("ユーザーアプリの判定", mJudgeDisablable.isDisablable(applicationInfo));
	}

	public void testDisablableApp() {
		ApplicationInfo applicationInfo = getAppInfo("com.google.android.email");
		assertTrue("無効化可能アプリの判定", mJudgeDisablable.isDisablable(applicationInfo));
	}

	public void testUndisablableApp() {
		ApplicationInfo applicationInfo = getAppInfo("android");
		assertFalse("無効化不可能なアプリの判定", mJudgeDisablable.isDisablable(applicationInfo));
	}

	/**
	 * @return
	 * @throws NameNotFoundException
	 */
	private ApplicationInfo getAppInfo(String packageName) {
		try {
			return getContext().getPackageManager()
					.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
}
