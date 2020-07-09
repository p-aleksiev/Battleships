package com.games.battleships.services;

import com.games.battleships.models.Square;
import com.games.battleships.services.contracts.IOService;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.logging.Logger;

@Service
public class IOServiceImpl implements IOService {

    private final Logger logger = Logger.getLogger(IOServiceImpl.class.getName());

    @Override
    public byte[] serializeGameField(Square[][] field) {
        byte[] serialized = null;
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream oot = new ObjectOutputStream(bos)) {

            oot.writeObject(field);
            oot.flush();
            serialized = bos.toByteArray();
        } catch (IOException ex) {
            logger.warning(ex.getMessage());
        }

        return serialized;
    }

    @Override
    public Square[][] deserializeGameField(byte[] serialized) {
        Square[][] field = null;
        try(ByteArrayInputStream bis = new ByteArrayInputStream(serialized);
            ObjectInput in = new ObjectInputStream(bis))    {

            Object o = in.readObject();
            field = (Square[][]) o;
        } catch (IOException | ClassNotFoundException ex) {
            logger.warning(ex.getMessage());
        }

        return field;
    }
}
