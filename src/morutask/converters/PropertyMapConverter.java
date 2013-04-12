package morutask.converters;

import java.util.Properties;

import com.leclercb.commons.api.properties.PropertyMap;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.collections.PropertiesConverter;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;

public class PropertyMapConverter extends PropertiesConverter {
	
	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class type) {
		return PropertyMap.class.isAssignableFrom(type);
	}
	
	@Override
	public Object unmarshal(
			HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		Properties properties = (Properties) super.unmarshal(reader, context);
		return new PropertyMap(properties);
	}
	
}
