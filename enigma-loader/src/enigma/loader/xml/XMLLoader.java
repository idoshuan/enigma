package enigma.loader.xml;

import enigma.core.Inventory;
import enigma.loader.core.Loader;
import enigma.loader.core.exceptions.file.ParseException;
import enigma.loader.core.validators.DefinitionValidator;
import enigma.loader.core.validators.FileValidator;
import enigma.loader.xml.generated.BTEEnigma;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;

public class XMLLoader implements Loader {

    @Override
    public Inventory load(String path) {
        new FileValidator(path, ".xml").validate();
        File file = new File(path);

        BTEEnigma enigma = parseXml(file);
        new DefinitionValidator(enigma).validate();

        return new XMLParser().parse(enigma);
    }

    private BTEEnigma parseXml(File file) {
        try {
            JAXBContext context = JAXBContext.newInstance(BTEEnigma.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (BTEEnigma) unmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            throw new ParseException(file.getName(), "XML", e);
        }
    }
}
