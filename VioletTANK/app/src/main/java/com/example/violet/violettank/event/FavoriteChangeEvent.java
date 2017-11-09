package com.example.violet.violettank.event;

import com.example.violet.violettank.data.model.Song;

/**
 * Created by violet.
 *
 * @ author VioLet.
 * @ email 286716191@qq.com
 * @ date 2017/11/8.
 */

public class FavoriteChangeEvent {

    public Song song;

    public FavoriteChangeEvent(Song song) {
        this.song = song;
    }
}
