package com.faithapps.android.maps;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class MapItemizedOverlay extends ItemizedOverlay{
    private List<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
    private Context mContext;

    public MapItemizedOverlay(Drawable defaultMarker, Context context) {
          super(boundCenterBottom(defaultMarker));
          mContext = context;
    }

    public void addOverlay(OverlayItem overlay) {
        mOverlays.add(overlay);
        populate();
    }

    @Override
    protected OverlayItem createItem(int i) {
      return mOverlays.get(i);
    }

    @Override
    public int size() {
      return mOverlays.size();
    }

}
