package com.nagopy.android.disabledapps.test.util;

import com.nagopy.android.disabledapps.util.AppStatus;

import android.test.AndroidTestCase;

public class AppStatusTest extends AndroidTestCase {

	private AppStatus mAppStatus;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		mAppStatus = new AppStatus("label", "packageName", false, false, false);
	}

	public void testラベル名のget() throws Exception {
		assertEquals("label", mAppStatus.getLabel());
	}

	public void testパッケージ名のget() throws Exception {
		assertEquals("packageName", mAppStatus.getPackageName());
	}

	public void testIsSystemのget() throws Exception {
		assertEquals(false, mAppStatus.isSystem());
	}

	public void testIsEnabledのget() throws Exception {
		assertEquals(false, mAppStatus.isEnabled());
	}

	public void testCanDisableのget() throws Exception {
		assertEquals(false, mAppStatus.canDisable());
	}

	public void testステータスのget_set() throws Exception {
		mAppStatus.setRunningStatus(1);
		assertEquals(1, mAppStatus.getRunningStatus());
	}
}
