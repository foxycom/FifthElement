package fifthelement.theelement.presentation.activities;


import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import fifthelement.theelement.BuildConfig;
import fifthelement.theelement.R;
import fifthelement.theelement.application.Helpers;
import fifthelement.theelement.application.Services;
import fifthelement.theelement.business.services.PlaylistService;
import fifthelement.theelement.business.services.SongListService;
import fifthelement.theelement.business.services.SongService;
import fifthelement.theelement.objects.Playlist;
import fifthelement.theelement.objects.Song;
import fifthelement.theelement.presentation.adapters.CompactSongsListAdapter;
import fifthelement.theelement.presentation.adapters.PlaylistListAdapter;
import fifthelement.theelement.presentation.constants.NotificationConstants;
import fifthelement.theelement.presentation.fragments.SeekerFragment;
import fifthelement.theelement.presentation.services.MusicService;
import fifthelement.theelement.presentation.services.MusicService.MusicBinder;
import fifthelement.theelement.presentation.services.NotificationService;
import fifthelement.theelement.presentation.util.DatabaseUtil;


public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private SongService songService;
    private SongListService songListService;
    private MusicService musicService;
    private PlaylistService playlistService;
    private Intent playIntent;
    private boolean musicBound = false;
    private Playlist currentPlaylist;

    public SongService getSongService() {
        return songService;
    }
    public MusicService getMusicService(){
        return musicService;
    }
    public PlaylistService getPlaylistService(){
        return playlistService;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create hamburger icon
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        mDrawer = Services.getDrawerService(this).getmDrawer();
        int versionCode = BuildConfig.VERSION_CODE;
        String versionName = BuildConfig.VERSION_NAME;

        TextView version = (TextView)findViewById(R.id.footer_item);

        version.setText("Version: " + versionName + versionCode);
        DatabaseUtil.copyDatabaseToDevice(this);

        songService = Services.getSongService();
        songListService = Services.getSongListService();
        playlistService = new PlaylistService();
        //Sets current song list to the list of all songs in app
        songListService.setSongList(songService.getSongs());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.header_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_song:
                Intent myIntent = new Intent(MainActivity.this, AddMusicActivity.class);
                MainActivity.this.startActivity(myIntent);
                return true;
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showDialog(final Song song) {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(this);
        builderSingle.setIcon(R.drawable.ic_add);
        builderSingle.setTitle("Select a Playlist:");

        final PlaylistListAdapter playlistListAdapter = new PlaylistListAdapter(this, playlistService.getPlaylists());

        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(playlistListAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AlertDialog.Builder builderInner = new AlertDialog.Builder(MainActivity.this);
                Playlist chosenPlaylist =  playlistService.getPlaylists().get(which);
                chosenPlaylist.addSong(song);
                //builderInner.setMessage(chosenPlaylist.getName()+" is the chosen playlist");
                builderInner.setTitle("Added to "+chosenPlaylist.getName());
                builderInner.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int which) {
                        dialog.dismiss();
                    }
                });
                builderInner.show();
            }
        });
        builderSingle.show();
    }

    public Playlist getCurrentPlaylist(){ return currentPlaylist;}
    public void setCurrentPlaylist(Playlist newPlaylist){ this.currentPlaylist = newPlaylist;}

    public void openPlaylistSongs(){
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.alert_dialog_custom));
        builderSingle.setIcon(R.drawable.ic_song_list);
        builderSingle.setTitle(currentPlaylist.getName()+" songs:");

        //final CompactSongsListAdapter compactSongsListAdapter = new CompactSongsListAdapter(this, currentPlaylist.getSongs());

        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        final CompactSongsListAdapter compactSongsListAdapter = new CompactSongsListAdapter(this, currentPlaylist.getSongs());
        builderSingle.setAdapter(compactSongsListAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                musicService.playSongAsync(currentPlaylist.getSongs().get(which));
            }
        });

        builderSingle.show();
    }

    public void startNotificationService(View v) {
        Intent serviceIntent = new Intent(MainActivity.this, NotificationService.class);
        serviceIntent.setAction(NotificationConstants.STARTFOREGROUND_ACTION);
        startService(serviceIntent);
    }

    private ServiceConnection musicConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicBinder binder = (MusicBinder)service;
            //get service
            musicService = binder.getService();
            Services.setMusicService(musicService);
            musicBound = true;

            createSeeker();


        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicBound = false;
        }
    };

    public void createSeeker() {
        SeekerFragment seeker = new SeekerFragment();//create the fragment instance
        Helpers.getFragmentHelper(this).createFragment(R.id.music_seeker, seeker);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(playIntent == null){
            playIntent = new Intent(this, MusicService.class);
            bindService(playIntent, musicConnection, Context.BIND_AUTO_CREATE);
            startService(playIntent);
        }
    }

    @Override
    protected void onDestroy() {
        stopService(playIntent);
        unbindService(musicConnection);
        musicService = null;
        super.onDestroy();
    }


}
