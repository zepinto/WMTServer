package pt.lsts.wmserver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WMSController {

    @Value("${tiles.folder:.}")
    String tilesFolder = ".";

    @GetMapping(value = "static/WMTSCapabilities.xml", produces = MediaType.TEXT_XML_VALUE)
    public @ResponseBody byte[] capabilities() throws IOException {
        return Files.readAllBytes(Paths.get(new ClassPathResource("/WMTSCapabilities.xml").getPath()));
    }

    @PostConstruct
    public void started() {
        Logger.getLogger(getClass().getSimpleName()).info("Loading tiles from "+tilesFolder);
    }
    
    @GetMapping(value = "/{folder}/{z}/{x}/{y}.png", produces = MediaType.IMAGE_PNG_VALUE)
    public @ResponseBody byte[] getTile(@PathVariable String folder, @PathVariable("z") int z, @PathVariable("x") int x, @PathVariable("y") int y)
            throws IOException, TileNotExistsException {
        Path path = Paths.get(tilesFolder, folder, "z" + z, "x" + x, "y" + y + ".png");
        System.out.println(path);
        if (Files.isReadable(path)) {
            return Files.readAllBytes(path);
        }
        else
            throw new TileNotExistsException();
    }

    @GetMapping(value = "/")
    public String getTile() {
        return "use URLs like /TileNVMaps/{z}/{x}/{y}.png";
    }
}
