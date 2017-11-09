package com.example.violet.violettank.event;

import com.example.violet.violettank.data.model.PlayList;

/**
 * Created by violet.
 *
 * @ author VioLet.
 * @ email 286716191@qq.com
 * @ date 2017/11/8.
 */

public class PlayListCreatedEvent {
    public PlayList playList;

    public PlayListCreatedEvent(PlayList playList) {
        this.playList = playList;
    }
}
