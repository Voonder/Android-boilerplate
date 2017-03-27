package com.voonapp.boilerplate;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

public class App extends Application {

//	public static final boolean ENCRYPTED = true;
//
//	private DaoSession daoSession;

	@Override
	public void onCreate() {
		super.onCreate();
		if (LeakCanary.isInAnalyzerProcess(this)) {
			return;
		}

		LeakCanary.install(this);

//		DevOpenHelper helper = new DevOpenHelper(this, ENCRYPTED ? "notes-db-encrypted" : "notes-db");
//		Database db = ENCRYPTED ? helper.getEncryptedWritableDb("super-secret") : helper.getWritableDb();
//		daoSession = new DaoMaster(db).newSession();
	}

//	public DaoSession getDaoSession() {
//		return daoSession;
//	}
}
