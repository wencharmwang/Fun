package com.wencharm.fun.ui.home;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.wencharm.fun.R;
import com.wencharm.fun.ui.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

	public static final String TAG = "MainActivity";

	@BindView(R.id.toolbar)
	Toolbar toolbar;

	@BindView(R.id.drawer_layout)
	DrawerLayout drawer;

	@BindView(R.id.nav_view)
	NavigationView navigationView;

	@BindView(R.id.fab)
	FloatingActionButton fab;

	@BindView(R.id.content)
	FrameLayout content;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		ButterKnife.bind(this);
		setSupportActionBar(toolbar);
		fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show());
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.addDrawerListener(toggle);
		toggle.syncState();
		navigationView.setNavigationItemSelectedListener(this);
		getSupportFragmentManager().beginTransaction().add(R.id.content, new GanksFragment()).addToBackStack(GanksFragment.TAG).commit();
		navigationView.getMenu().getItem(0).setChecked(true);
	}

	@Override
	public void onBackPressed() {
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		if (drawer.isDrawerOpen(GravityCompat.START)) {
			drawer.closeDrawer(GravityCompat.START);
		} else if (getSupportFragmentManager().findFragmentById(R.id.content) instanceof GanksFragment) {
			Snackbar.make(fab, "Are you sure to close app?", Snackbar.LENGTH_LONG).setAction("Action", v -> finish()).show();
		} else super.onBackPressed();
	}

	@Override
	public boolean onNavigationItemSelected(MenuItem item) {
		// Handle navigation view item clicks here.
		int id = item.getItemId();
		if (id == R.id.nav_home) {
			if (getSupportFragmentManager().findFragmentById(R.id.content) instanceof GanksFragment) ;
			else getSupportFragmentManager().beginTransaction().replace(R.id.content, new GanksFragment()).addToBackStack(GanksFragment.TAG).commit();
		} else if (id == R.id.nav_gallery) {

		} else if (id == R.id.nav_manage) {

		} else if (id == R.id.nav_share) {

		}
		item.setChecked(true);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}
}
