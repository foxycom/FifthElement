package fifthelement.theelement.presentation;

import android.app.ListActivity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;


import java.util.ArrayList;

import fifthelement.theelement.R;
import fifthelement.theelement.objects.Album;
import fifthelement.theelement.objects.Author;
import fifthelement.theelement.objects.Playlist;
import fifthelement.theelement.objects.Song;
import fifthelement.theelement.objects.SongsListAdapter;
import fifthelement.theelement.persistence.PlaylistPersistence;
import fifthelement.theelement.persistence.stubs.PlaylistPersistenceStub;

public class SearchFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        PlaylistPersistence playlist = new PlaylistPersistenceStub(
                1, "Test Playlist");

        View view = inflater.inflate(R.layout.fragment_search,
                container,
                false);

        String[] songName = {"Nice For What", "God's Plan", "This Is America", "Yes Indeed", "No Tears Left To Cry"};
        String[] authorNames = {"Drake", "Drake", "Childish Gambino", "Lil Baby & Drake", "Ariana Grande" };
        ArrayList<Song> songs = new ArrayList<Song>();
        for(int i = 0; i < 12; i++) {
            Song song = new Song(i, songName[i%5], "empty");
            song.addAlbum(new Album(i, songName[i%5]));
            song.addAuthor(new Author(i, authorNames[i%5]));
            songs.add(song);
        }

        ListView listView = view.findViewById(R.id.song_list_view);
        SongsListAdapter songListAdapter = new SongsListAdapter(getActivity(),
                songs );

        listView.setAdapter(songListAdapter);

        return view;
    }

    /*
    @Override
    public void onCreateOptionsMenu (Menu menu, MenuInflater inflater){
        //inflater.inflate(R.menu.search, menu);
        //MenuItem item = menu.findItem(R.id.action_search);
        SearchView sv = new SearchView(((YourActivity) getActivity()).getSupportActionBar().getThemedContext());
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        MenuItemCompat.setActionView(item, sv);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                System.out.println("search query submit");
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                System.out.println("tap");
                return false;
            }
        });
    }*/
}
