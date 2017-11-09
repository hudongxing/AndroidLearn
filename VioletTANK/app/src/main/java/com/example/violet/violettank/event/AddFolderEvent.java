package com.example.violet.violettank.event;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by violet.
 *
 * @ author VioLet.
 * @ email 286716191@qq.com
 * @ date 2017/11/3.
 */

public class AddFolderEvent {

    public List<File> folders = new ArrayList<>();

    public AddFolderEvent(File file) {
        folders.add(file);
    }

    public AddFolderEvent(List<File> files) {
        if (files != null) {
            folders = files;
        }
    }
}
