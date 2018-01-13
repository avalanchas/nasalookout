package com.elliecoding.nasalookout.entities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

/**
 * Serialized wrapper class for bitmaps so that any bitmap can be stored easily on the disk and transmitted in intent
 * extras. To use it simply pass the original bitmap to the constructor as early as possible
 */
public class SerializedBitmap implements Serializable {

    private static final long serialVersionUID = -4415639080198755856L;

    private Bitmap bitmap;

    public SerializedBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    /**
     * Converts the Bitmap into a byte array for serialization
     */
    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteStream);
        byte bitmapBytes[] = byteStream.toByteArray();
        out.write(bitmapBytes, 0, bitmapBytes.length);
    }

    /**
     * Deserializes a byte array representing the Bitmap and decodes it
     */
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        int bite;
        while ((bite = in.read()) != -1)
            byteStream.write(bite);
        byte bitmapBytes[] = byteStream.toByteArray();
        bitmap = BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.length);
    }

    /**
     * Returns the original bitmap stored in this wrapper
     *
     * @return The regular bitmap
     */
    public Bitmap getBitmap() {
        return bitmap;
    }
}
