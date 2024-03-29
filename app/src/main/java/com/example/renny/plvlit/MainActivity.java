package com.example.renny.plvlit;

import  android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**Created By Renny on 4/17/2017
 *
  */
public class MainActivity extends AppCompatActivity {

    private AlbumsAdapter adapter;
    private List<Album> albumList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initCollapsingToolbar();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyler_view);

        albumList = new ArrayList<>();
        adapter = new AlbumsAdapter(this, albumList);

        RecyclerView.LayoutManager mlayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mlayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2,dpToPx(10),true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        prepareAlbums();

        try {
            Glide.with(this).load(R.drawable.cover).into((ImageView)findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Initialising the toolbar
     * It will be shown and hidden on scroll
     */
    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar=(CollapsingToolbarLayout)findViewById
                (R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout=(AppBarLayout)findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);
        //hiding and showing the title when the toolbar is expanded or collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow=false;
            int scrollRange=-1;
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1 ){
                    scrollRange=appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset ==0){
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                }else if (isShow){
                    collapsingToolbar.setTitle(" ");
                    isShow=false;
                }
            }
        });
    }

    /**
     * adding a few albums for testing
     */
    private void prepareAlbums() {
        int[] covers= new int[]{
                R.drawable.album1,
                R.drawable.album2,
                R.drawable.album3,
                R.drawable.album4,
                R.drawable.album5,
                R.drawable.album6,
                R.drawable.album7,
                R.drawable.album8,
                R.drawable.album9,
                R.drawable.album10,
                R.drawable.album11,};
        Album a= new Album("True Romance",13,covers[0]);
        albumList.add(a);

        a=new Album("Xscape",8,covers[1]);
        albumList.add(a);

        a=new Album("Maroon 5",11,covers[2]);
        albumList.add(a);

        a=new Album("Born to Die",12,covers[3]);
        albumList.add(a);

        a=new Album("HoneyMoon",14,covers[4]);
        albumList.add(a);

        a=new Album("I Need a Doctor",1,covers[5]);
        albumList.add(a);

        a=new Album("Loud",11,covers[6]);
        albumList.add(a);

        a = new Album("Legend", 14, covers[7]);
        albumList.add(a);

        a = new Album("Hello", 11, covers[8]);
        albumList.add(a);

        a = new Album("Greatest Hits", 17, covers[9]);
        albumList.add(a);

        adapter.notifyDataSetChanged();
    }
    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    private class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public  GridSpacingItemDecoration(int spanCount,int spacing , boolean includeEdge) {
            this.spanCount=spanCount;
            this.spacing=spacing;
            this.includeEdge=includeEdge;
    }
@Override
        public void getItemOffsets(Rect outRect,View view,RecyclerView parent,RecyclerView.State state){
        int position=parent.getChildAdapterPosition(view);
        int column=position % spanCount;

        if (includeEdge){
            outRect.left=spacing-column * spacing/spanCount;
            outRect.right=(column+1)*spacing/spanCount;
            if (position<spanCount){
                outRect.top=spacing;
            }
            outRect.bottom=spacing;
        }else{
            outRect.left=column*spacing/spanCount;
            outRect.right=spacing-(column+1)*spacing/spanCount;
            if(position>=spanCount){
                outRect.top=spacing;
            }
        }

    }
 }
 private int dpToPx(int dp) {
        Resources r= getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,r.getDisplayMetrics()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
