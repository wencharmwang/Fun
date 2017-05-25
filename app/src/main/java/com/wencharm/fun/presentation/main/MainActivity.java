package com.wencharm.fun.presentation.main;

import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.wencharm.fun.R;
import com.wencharm.fun.app.App;
import com.wencharm.fun.presentation.BaseActivity;
import com.wencharm.fun.presentation.home.GanksFragment;

import java.util.ArrayList;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

	public static final String TAG = "MainActivity";

	Toolbar toolbar;
	DrawerLayout drawer;
	NavigationView navigationView;
	FloatingActionButton fab;
	FrameLayout content;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		navigationView = (NavigationView) findViewById(R.id.nav_view);
		fab = (FloatingActionButton) findViewById(R.id.fab);
		content = (FrameLayout) findViewById(R.id.content);
		setSupportActionBar(toolbar);
		fab.setOnClickListener(view -> {
			Snackbar.make(view, "start syncing", Snackbar.LENGTH_SHORT).show();
			Single.create(s -> {
				ArrayList<String> path = ImageSyncService.readLastDateFromMediaStore(this, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				ImageSyncService.addTag(path);
			}).subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe();
		});
		fab.setOnLongClickListener(v -> {
			ViewGroup view = (ViewGroup) inflater().inflate(R.layout.home_edit_text, null);
			EditText editText = (EditText) view.findViewById(R.id.text);
			editText.setText(App.sp.getString(ImageSyncService.TAG_ON_IMAGE, ImageSyncService.TEXT));
			AlertDialog dialog = new AlertDialog.Builder(this).setView(view).setPositiveButton("OK", (d, w) -> {
				App.sp.edit().putString(ImageSyncService.TAG_ON_IMAGE, editText.getText().toString()).apply();
			}).show();
			return true;
		});
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.addDrawerListener(toggle);
		toggle.syncState();
		navigationView.setNavigationItemSelectedListener(this);
		getSupportFragmentManager().beginTransaction().add(R.id.content, new GanksFragment()).commit();
		navigationView.getMenu().getItem(0).setChecked(true);
//		startService(new Intent(MainActivity.this, ImageSyncService.class));
	}

	@Override
	public void onBackPressed() {
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		if (drawer.isDrawerOpen(GravityCompat.START)) {
			drawer.closeDrawer(GravityCompat.START);
		} else super.onBackPressed();
	}

	@Override
	public boolean onNavigationItemSelected(@NonNull MenuItem item) {
		// Handle navigation view item clicks here.
		int id = item.getItemId();
		if (id == R.id.nav_home) {
			if (getSupportFragmentManager().findFragmentById(R.id.content) instanceof GanksFragment) ;
			else getSupportFragmentManager().beginTransaction().replace(R.id.content, new GanksFragment()).commit();
		} else if (id == R.id.nav_gallery) {

		} else if (id == R.id.nav_manage) {

		} else if (id == R.id.nav_share) {

		}
		item.setChecked(true);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}
}
