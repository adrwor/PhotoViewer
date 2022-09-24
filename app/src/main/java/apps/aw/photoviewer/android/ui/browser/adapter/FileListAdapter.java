package apps.aw.photoviewer.android.ui.browser.adapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import apps.aw.photoviewer.R;
import apps.aw.photoviewer.java.ListItem;
import apps.aw.photoviewer.java.ItemListWithFocus;

public class FileListAdapter extends RecyclerView.Adapter<FileListAdapter.ViewHolder> {

    private static final String TAG = "FileListAdapter";

    private ItemListWithFocus itemListWithFocus; //contains the filelist as well as the current selection
    private Context context;

    /**
     * Constructor.
     * @param itemListWithFocus
     */
    public FileListAdapter(ItemListWithFocus itemListWithFocus, Context context) {
//        Log.i("FileListAdapter", "FileListAdapter()");
        this.itemListWithFocus = itemListWithFocus;
        this.context = context;
    }

    /**
     * Sets the file list (with selection and state).
     * @param itemListWithFocus FileListSelection object
     */
    public void setFileListSelection(ItemListWithFocus itemListWithFocus) {
        assert itemListWithFocus != null;
//        Log.i("FileListAdapter", "setFileListSelection() length: " + fileList.getListSize());
        this.itemListWithFocus = itemListWithFocus;
        Log.i(TAG, "setFileListSelection is null: " + (this.itemListWithFocus == null));
        notifyDataSetChanged();
    }

    public void setFocusedItem(int index) {
        itemListWithFocus.setFocus(index);
        notifyItemChanged(index);
    }

    /**
     * Sets the selection.
     * @param selection FileListSelection.Selection object
     */
    public void setSelection(int selection) {
        int oldSelectionIndex = itemListWithFocus.getFocus(); //store old selection position
        itemListWithFocus.setFocus(selection);   //update selection position
        notifyItemChanged(oldSelectionIndex);    //update view at old position
        notifyItemChanged(itemListWithFocus.getFocus()); //update view at new position
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i("FileListAdapter", "onCreateViewHolder()");
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.file_view, parent, false);
        return new ViewHolder(v);   //return a new viewholder
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.i("FileListAdapter", "onBindViewHolder()");
        int p = holder.getAbsoluteAdapterPosition();
        ListItem listItem = itemListWithFocus.getItemList().get(p);
        holder.text.setText(listItem.name); //set text
        switch (listItem.type) {    // set icon
            case ROOT:
                holder.icon.setImageResource(R.drawable.ic_root);
                break;
            case ACTION:
                holder.icon.setImageResource(R.drawable.ic_add);
                break;
            case DIRECTORY:
                holder.icon.setImageResource(R.drawable.ic_folder);
                break;
            case IMAGE:
                holder.icon.setImageResource(R.drawable.ic_image);
                break;
            case BROKEN_IMAGE:
                holder.icon.setImageResource(R.drawable.ic_broken_image);
                break;
            case FILE:
                holder.icon.setImageResource(R.drawable.ic_file);
                break;
        }
        if(position == itemListWithFocus.getFocus()) {
            holder.mView.setBackgroundResource(R.color.colorItemFocused);
//            holder.text.setTextColor(context.getResources().getColor(R.color.colorTextItemFocused));
        } else {
            holder.mView.setBackgroundResource(R.color.colorItemDefault);
//            holder.text.setTextColor(context.getResources().getColor(R.color.colorTextItemDefault));
        }
    }

    @Override
    public int getItemCount() {
//        Log.i("FileListAdapter", "getItemCount()");
        // TODO: this still gives an npe (itemListWithFocus is null) at the start of the app
        //      -> why?
        return itemListWithFocus.getListSize();
    }

    /**
     * ViewHolder
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView icon;
        public TextView text;
        public View mView;

        public ViewHolder(View itemView) {
            super(itemView);
            Log.i("FileListAdapter", "ViewHolder()");
            icon = (ImageView) itemView.findViewById(R.id.iconImageView);
            text = (TextView) itemView.findViewById(R.id.fileNameTextView);
            mView = itemView;
        }
    }

}
