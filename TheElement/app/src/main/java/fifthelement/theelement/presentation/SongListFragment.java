package fifthelement.theelement.presentation;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import java.util.ArrayList;

import fifthelement.theelement.R;
import fifthelement.theelement.objects.Album;
import fifthelement.theelement.objects.Author;
import fifthelement.theelement.objects.Playlist;
import fifthelement.theelement.objects.Song;
import fifthelement.theelement.objects.SongsListAdapter;
import fifthelement.theelement.persistence.PlaylistPersistence;
import fifthelement.theelement.persistence.stubs.PlaylistPersistenceStub;
import fifthelement.theelement.persistence.stubs.SongPersistenceStub;

public class SongListFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        PlaylistPersistence playlist = new PlaylistPersistenceStub(
                1, "Test Playlist");

        View view = inflater.inflate(R.layout.song_list_fragment,
                container,
                false);

        String[] songName = {"Nice For What", "God's Plan", "This Is America", "Yes Indeed", "No Tears Left To Cry"};
        String[] authorNames = {"Drake", "Drake", "Childish Gambino", "Lil Baby & Drake", "Ariana Grande" };
        ArrayList<Song> songs = new ArrayList<Song>();
        for(int i = 0; i < 12; i++) {
            Song song = new Song(i, songName[i%5], "");
            song.addAlbum(new Album(i, songName[i%5]));
            song.addAuthor(new Author(i, authorNames[i%5]));
            songs.add(song);
        }

        ListView listView = view.findViewById(R.id.song_list_view);
        SongsListAdapter songListAdapter = new SongsListAdapter(getActivity(), songs );

        listView.setAdapter(songListAdapter);

        return view;
    }
}
