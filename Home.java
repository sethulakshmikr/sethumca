package com.example.sethulakshmi.admininspect;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sethulakshmi.admininspect.Common.Common;
import com.example.sethulakshmi.admininspect.Model.User;
import com.example.sethulakshmi.admininspect.ViewHolder.UserViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener   {

TextView txt_name,txt_mail;
    CircleImageView imageView;

    private RecyclerView recyclerView;
    //private StorageReference mStorageRef;
    public DatabaseReference DRAdmin, login,db,DBuseradmin;
    String m,n,e;
    StorageReference storageRef;

FragmentManager fm;
    FirebaseRecyclerAdapter<User, UserViewHolder> itemadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        db = FirebaseDatabase.getInstance().getReference("USERS");
      //  DBuseradmin= FirebaseDatabase.getInstance().getReference("USERADMIN").child(Common.currentUser.getPhonenumber());
        recyclerView = (RecyclerView) findViewById(R.id.recycler_user_details);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(mLayoutManager);
        Bundle bundle = getIntent().getExtras();

        final String text= bundle.getString("uname");
        Toast.makeText(this, ""+text, Toast.LENGTH_SHORT).show();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        View headerView=navigationView.getHeaderView(0);
        txt_name=(TextView)headerView.findViewById(R.id.admin_name);
        txt_mail=(TextView)headerView.findViewById(R.id.admin_mailid);
        txt_name.setText(Common.currentUser.getFirstname().toString());
        txt_mail.setText(Common.currentUser.getEmailid().toString());
        imageView=(CircleImageView)headerView.findViewById(R.id.profile_image);
        Picasso.with(getBaseContext()).load(Common.currentUser.getImageUri())
                           .into(imageView);
        loadUserDetails();
    }

    public void loadUserDetails() {
        itemadapter = new FirebaseRecyclerAdapter<User,UserViewHolder>(User.class,
                R.layout.userdetails_list,
                UserViewHolder.class,
                db) {
            @Override
            protected void populateViewHolder(UserViewHolder viewHolder, User model, int position) {

//                viewHolder.user_name.setText(model.getFname());
                viewHolder.user_name1.setText(model.getFname());
                //Picasso.with(getBaseContext()).load(model.getImage())
                       // .into(viewHolder.user_image1);
                Glide.with(getBaseContext()).load(model.getImage()).into(viewHolder.user_image1);

            }
        };
        recyclerView.setAdapter(itemadapter);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Intent intent;
        // Handle navigation view item clicks here.
        int id = item.getItemId();
      Fragment fragment=null;
        if (id == R.id.adduser) {

            fragment=new AddUser();
            // Handle the camera action
        } else if (id == R.id.addwork) {
        fragment=new WorkAdd();
        } else if (id == R.id.teamcreation) {
            fragment=new TeamCreationn();

        } else if (id == R.id.search) {
            fragment=new FragmentSearchMenu();

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
       if (fragment != null) {

            fm=getFragmentManager();

           FragmentTransaction fragmentTransaction=fm.beginTransaction();
           fragmentTransaction.replace(R.id.screen_area,fragment).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
