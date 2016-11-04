/**
 * 
 */
package com.suidifu.hathaway.web.format;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import com.demo2do.core.persistence.GenericDaoSupport;
import com.demo2do.core.utils.ClasspathScanner;
import com.demo2do.core.web.format.PersistentFormat;

/**
 * @author wukai
 *
 */
public class PersistentAnnotationFormatterFactory implements AnnotationFormatterFactory<PersistentFormat>, InitializingBean {

	@Value("#{config['entity.package']}")
	private String entityPackage;

	@Autowired
	private GenericDaoSupport genericDaoSupport;

	private Set<Class<?>> fieldTypes;

	/* 
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() throws Exception {
		this.fieldTypes = this.scanPersistentFormatterClass();
	}

	/**
	 * 
	 * @return
	 */
	private Set<Class<?>> scanPersistentFormatterClass() {

		Set<Class<?>> persistentFormatterClass = new HashSet<Class<?>>();

		// scan entity package
		ClasspathScanner<Object> classpathScanner = new ClasspathScanner<Object>();
		classpathScanner.find(new ClasspathScanner.AnnotatedWith(PersistentFormat.class), StringUtils.split(entityPackage, ",") );
		persistentFormatterClass.addAll(classpathScanner.getClasses());

		return persistentFormatterClass;
	}

	/* 
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.format.AnnotationFormatterFactory#getFieldTypes()
	 */
	public Set<Class<?>> getFieldTypes() {
		return fieldTypes;
	}

	/* 
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.format.AnnotationFormatterFactory#getPrinter(java.lang.annotation.Annotation, java.lang.Class)
	 */
	public Printer<?> getPrinter(PersistentFormat annotation, Class<?> fieldType) {
		return null;
	}

	/* 
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.format.AnnotationFormatterFactory#getParser(java.lang.annotation.Annotation, java.lang.Class)
	 */
	public Parser<?> getParser(PersistentFormat annotation, final Class<?> fieldType) {

		return new Parser<Object>() {

			public Object parse(String text, Locale locale) throws ParseException {
				return StringUtils.isNotEmpty(text) ? genericDaoSupport.load(fieldType, new Long(text)) : null;
			}

		};
	}
}
