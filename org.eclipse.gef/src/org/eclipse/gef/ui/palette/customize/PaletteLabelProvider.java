package org.eclipse.gef.ui.palette.customize;

import java.util.*;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.TreeViewer;

import org.eclipse.gef.internal.InternalImages;
import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteEntry;

import org.eclipse.draw2d.ColorConstants;

/**
 * This class is the ILabelProvider for the {@link org.eclipse.jface.viewers.TreeViewer}
 * used in {@link org.eclipse.gef.ui.palette.customize.PaletteCustomizerDialog}.
 * 
 * @author Pratik Shah
 */
class PaletteLabelProvider 
	implements ILabelProvider, IColorProvider 
{

private TreeViewer treeviewer;
private Map imageCache = new HashMap();

/**
 * Constructor
 * 
 * @param viewer	The TreeViewer for which this instance is a LabelProvider
 */
public PaletteLabelProvider(TreeViewer viewer) {
	treeviewer = viewer;
}

/**
 * @see org.eclipse.jface.viewers.IColorProvider#getBackground(Object)
 */
public Color getBackground(Object element) {
	return null;
}

private Image getCachedImage(ImageDescriptor descriptor) {
	Image image = (Image)imageCache.get(descriptor);
	if (image == null) {
		image = descriptor.createImage();
		imageCache.put(descriptor, image);
	}
	return image;
}

/**
 * @see org.eclipse.jface.viewers.IColorProvider#getForeground(Object)
 */
public Color getForeground(Object element) {
	PaletteEntry entry = (PaletteEntry)element;
	if (!entry.isVisible() || !entry.getParent().isVisible()) {
		return ColorConstants.gray;
	}
	return null;
}

/**
 * @see org.eclipse.jface.viewers.ILabelProvider#getImage(Object)
 */
public Image getImage(Object element) {
	PaletteEntry entry = (PaletteEntry)element;
	ImageDescriptor descriptor = entry.getSmallIcon();
	if (descriptor == null && (entry instanceof PaletteContainer)) {
		descriptor = InternalImages.DESC_FOLDER_OPEN;
	}
	if (descriptor == null)
		return null;
	return getCachedImage(descriptor);
}

/**
 * @see org.eclipse.jface.viewers.ILabelProvider#getText(Object)
 */
public String getText(Object element) {
	return ((PaletteEntry)element).getLabel();
}

/**
 * Not implemented
 * 
 * @see org.eclipse.jface.viewers.IBaseLabelProvider#addListener(ILabelProviderListener)
 */
public void addListener(ILabelProviderListener listener) {
}

/**
 * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
 */
public void dispose() {
	Iterator images = imageCache.values().iterator();
	while (images.hasNext())
		((Image)images.next()).dispose();
	imageCache = null;
}

/**
 * @see org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(Object, String)
 */
public boolean isLabelProperty(Object element, String property) {
	return false;
}

/**
 * Not implemented
 * 
 * @see org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(ILabelProviderListener)
 */
public void removeListener(ILabelProviderListener listener) {
}

}
