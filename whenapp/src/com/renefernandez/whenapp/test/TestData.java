package com.renefernandez.whenapp.test;

import java.util.Date;

import com.renefernandez.whenapp.model.Moment;

/**
 * Clase con datos de prueba utilizados antes de que se incluyera la BD.
 * 
 * @author rene
 * 
 */
public class TestData {

	public static Moment[] getTestMoments() {

		Moment[] moments = new Moment[] {

				new Moment("Moment 1 has a very large text").withDate(
						new Date()).withLocation(43.35560534, -5.850938559),
				new Moment("Moment 2").withDate(new Date()).withLocation(
						43.36560534, -5.850938559),
				new Moment("Moment 3").withDate(new Date()).withLocation(
						43.37560534, -5.850938559)

		};

		return moments;

	}

}
