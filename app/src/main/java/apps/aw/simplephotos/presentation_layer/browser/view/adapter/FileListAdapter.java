package apps.aw.simplephotos.presentation_layer.browser.view.adapter;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import apps.aw.simplephotos.R;
import apps.aw.simplephotos.presentation_layer.browser.utils.FileList;

public class FileListAdapter extends RecyclerView.Adapter<FileListAdapter.ViewHolder> {

    private FileList fileList; //contains the filelist as well as the current selection

    /**
     * Constructor.
     * @param fileList
     */
    public FileListAdapter(FileList fileList) {
//        Log.i("FileListAdapter", "FileListAdapter()");
        this.fileList = fileList;
    }

    /**
     * Sets the file list (with selection and state).
     * @param fileList FileListSelection object
     */
    public void setFileListSelection(FileList fileList) {
//        Log.i("FileListAdapter", "setFileListSelection() length: " + fileList.getListSize());
        this.fileList = fileList;
        notifyDataSetChanged();
    }

    public void setFocusedItem(int index) {
        fileList.setFocus(index);
        notifyItemChanged(index);
    }

    /**
     * Sets the selection.
     * @param selection FileListSelection.Selection object
     */
    public void setSelection(int selection) {
        int oldSelectionIndex = fileList.getFocus(); //store old selection position
        fileList.setFocus(selection);   //update selection position
        notifyItemChanged(oldSelectionIndex);    //update view at old position
        notifyItemChanged(fileList.getFocus()); //update view at new position
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
        //holder.icon.setImageDrawable(null);   //TODO: set drawable according to file type
        int p = holder.getAdapterPosition();
        holder.text.setText(fileList.getFileList().get(p)); //set text
        if(position == fileList.getFocus()) {
            holder.mView.requestFocus();
        }
    }

    @Override
    public int getItemCount() {
//        Log.i("FileListAdapter", "getItemCount()");
        return fileList.getListSize();
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
            icon = (ImageView) itemView.findViewById(R.id.imageView2);
            text = (TextView) itemView.findViewById(R.id.textView4);
            mView = itemView;
        }
    }

}
