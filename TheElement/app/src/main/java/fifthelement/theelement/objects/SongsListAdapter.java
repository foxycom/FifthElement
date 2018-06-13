package fifthelement.theelement.objects;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import fifthelement.theelement.R;

public class SongsListAdapter extends BaseAdapter {
    Context context;
    ArrayList<Song> songs;
    LayoutInflater inflater;

    public SongsListAdapter(Context context, ArrayList<Song> songs) {
        this.context = context;
        this.songs = songs;
        inflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return songs.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.fragment_song_list_item, null);
        TextView songName = (TextView) view.findViewById(R.id.song_name_list);
        TextView albumName = (TextView) view.findViewById(R.id.album_name_list);

        Song printSong = songs.get(i);
        ArrayList<Author> author = printSong.getAuthors();
        String albums = "";
        for(int j = 0; j < author.size(); j++){
            albums += author.get(j).getName();
            if(j < author.size()-1) {
                albums += ", ";
            }
        }

        songName.setText(printSong.getName());
        albumName.setText(albums);
        return view;
    }
}
