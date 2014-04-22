package com.renefernandez.whenapp.business.pictures;

import java.io.File;

import android.os.Environment;

/**
 * Devuelve la posición del directorio de almacenamiento para Froyo.
 * @author rene
 *
 */
public final class FroyoAlbumDirFactory extends AlbumStorageDirFactory {

	@Override
	public File getAlbumStorageDir(String albumName) {
		// TODO Auto-generated method stub
		return new File(
		  Environment.getExternalStoragePublicDirectory(
		    Environment.DIRECTORY_PICTURES
		  ), 
		  albumName
		);
	}
}
