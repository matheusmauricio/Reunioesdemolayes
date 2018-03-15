package com.example.matheus.reunioesdemolayes;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import domain.controller.Informacoes;
import domain.controller.ListarCapitulo;
import domain.controller.ProximasReunioes;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        SimpleDateFormat dateFormat_hora = new SimpleDateFormat("HH:mm:ss");
        Date data = new Date();

        Calendar  cal = Calendar.getInstance();
        cal.setTime(data);
        Date data_atual = cal.getTime();
        String hora_atual = dateFormat_hora.format(data_atual);

    }

    public void notificacao(){
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //PendingIntent p = PendingIntent.getActivity(this, 0, new Intent(this, Atividade2.class), 0);

        SimpleDateFormat dateFormat_hora = new SimpleDateFormat("HH:mm:ss");
        Date data = new Date();

        Calendar  cal = Calendar.getInstance();
        cal.setTime(data);
        Date data_atual = cal.getTime();
        String hora_atual = dateFormat_hora.format(data_atual);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setTicker("Ticker Texto");
        builder.setContentTitle("TÍtulo");
        builder.setContentText("Descrição");
        builder.setSmallIcon(R.drawable.ic_menu_gallery);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.side_nav_bar));
        //builder.setContentIntent(p);

        NotificationCompat.InboxStyle style = new NotificationCompat.InboxStyle();
        String [] descs = new String[]{"Descrição 1", "Descrição 2", "Descrição 3", "Descrição 4", hora_atual};
        for(int i = 0; i < descs.length; i++){
            style.addLine(descs[i]);
        }
        builder.setStyle(style);

        Notification n = builder.build();
        n.vibrate = new long[]{150, 300, 150, 600};
        n.flags = Notification.FLAG_AUTO_CANCEL;
        nm.notify(R.drawable.ic_menu_camera, n);

        try{
            Uri som = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone toque = RingtoneManager.getRingtone(this, som);
            toque.play();
        }
        catch(Exception e){

        }
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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_listarCapitulo) {
            Intent irParaTelaListarCapitulo = new Intent(this, ListarCapitulo.class);
            startActivity(irParaTelaListarCapitulo);
        } else if (id == R.id.nav_informacoes) {
            Intent irParaTelaInformacoes = new Intent(this, Informacoes.class);
            startActivity(irParaTelaInformacoes);
        } else if (id == R.id.nav_proximasReunioes) {
            Intent irParaTelaProximasReunioes = new Intent(this, ProximasReunioes.class);
            startActivity(irParaTelaProximasReunioes);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
