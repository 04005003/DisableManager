package com.nagopy.android.disabledapps.util.filter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.util.SparseArray;

import com.nagopy.android.disabledapps.util.AppStatus;

/**
 * アプリ一覧を、条件を指定してフィルタリングするクラス
 */
public class AppsFilter {

	/**
	 * 編集前のオリジナルを保持するリスト
	 */
	private ArrayList<AppStatus> originalAppList;

	/**
	 * 条件群を保持するSparseArray
	 */
	private SparseArray<AppFilterCondition> conditions;

	/**
	 * 無効化済み
	 */
	public static final int DISABLED = 0x01;

	/**
	 * 無効化可能だがまだ有効なシステムアプリ
	 */
	public static final int DISABLABLE_AND_ENABLED_SYSTEM = 0x02;

	/**
	 * 無効化できないシステムアプリ
	 */
	public static final int UNDISABLABLE_SYSTEM = 0x04;

	/**
	 * ユーザーアプリ
	 */
	public static final int USER_APPS = 0x08;

	/**
	 * コンストラクタ<br>
	 * リストの初期化などを行う
	 */
	public AppsFilter() {
		originalAppList = new ArrayList<AppStatus>();
		conditions = new SparseArray<AppFilterCondition>();
		conditions.put(DISABLED, new AppFilterCondition() {
			@Override
			public boolean valid(final AppStatus appStatus) {
				// 無効化済み
				return !appStatus.isEnabled();
			}
		});

		conditions.put(DISABLABLE_AND_ENABLED_SYSTEM, new AppFilterCondition() {
			@Override
			public boolean valid(final AppStatus appStatus) {
				// システムで、無効化可能で、まだ有効なアプリ
				return appStatus.isSystem() && appStatus.canDisable() && appStatus.isEnabled();
			}
		});

		conditions.put(UNDISABLABLE_SYSTEM, new AppFilterCondition() {
			@Override
			public boolean valid(final AppStatus appStatus) {
				// 無効化できないシステムアプリ
				return appStatus.isSystem() && !appStatus.canDisable();
			}
		});

		conditions.put(USER_APPS, new AppFilterCondition() {
			@Override
			public boolean valid(final AppStatus appStatus) {
				// 通常のアプリ
				return !appStatus.isSystem();
			}
		});
	}

	/**
	 * ソートする<br>
	 * 大文字小文字は区別しない
	 * @param list ソートしたいリスト
	 * @return ソート後のリスト
	 */
	public ArrayList<AppStatus> sort(ArrayList<AppStatus> list) {
		Comparator<AppStatus> comparator = new Comparator<AppStatus>() {
			@Override
			public int compare(final AppStatus obj0, final AppStatus obj1) {
				String label0 = ((AppStatus) obj0).getLabel();
				String label1 = ((AppStatus) obj1).getLabel();

				int ret = label0.compareToIgnoreCase(label1);
				// ラベルで並び替え、同じラベルがあったらパッケージ名で
				if (ret == 0) {
					String pkgName0 = ((AppStatus) obj0).getPackageName();
					String pkgName1 = ((AppStatus) obj1).getPackageName();
					ret = pkgName0.compareToIgnoreCase(pkgName1);
				}
				return ret;
			}
		};

		Collections.sort(list, comparator);

		return list;
	}

	/**
	 * すべてのアプリを登録しておく<br>
	 * ソートする場合は {@link #sort(ArrayList)}
	 * @param original
	 *           登録するリスト
	 */
	public void setOriginalAppList(ArrayList<AppStatus> original) {
		this.originalAppList = original;
	}

	/**
	 * フィルターを実行して、結果を返す
	 * @param key
	 *           実行するフィルタのキー
	 * @return フィルター結果
	 */
	public ArrayList<AppStatus> execute(int key) {
		AppFilterCondition condition = conditions.get(key);
		ArrayList<AppStatus> filtered = new ArrayList<AppStatus>();
		for (AppStatus appStatus : originalAppList) {
			if (condition.valid(appStatus)) {
				filtered.add(appStatus);
			}
		}
		return filtered;
	}

	/**
	 * 保存しているコレクションのクリア
	 */
	public void deallocate() {
		originalAppList = null;
		conditions = null;
	}
}