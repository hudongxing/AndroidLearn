package com.example.violet.violettank.player;

import com.example.violet.violettank.data.model.PlayList;
import com.example.violet.violettank.data.model.Song;

/**
 * Created by violet.
 *
 * @ author VioLet.
 * @ email 286716191@qq.com
 * @ date 2017/11/9.
 */

public interface IPlayback {
    boolean play();

    boolean play(PlayList list);

    boolean play(PlayList list, int startIndex);

    boolean play(Song song);

}
