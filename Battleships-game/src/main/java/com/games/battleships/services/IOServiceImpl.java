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
            return serialized;
        } catch (IOException ex) {
            logger.warning(ex.toString());
        }

        return serialized;
    }

    @Override
    public Square[][] deserializeGameField(byte[] serializedField) {
        Square[][] field = null;
        try(ByteArrayInputStream bis = new ByteArrayInputStream(serializedField);
            ObjectInput in = new ObjectInputStream(bis))    {

            Object o = in.readObject();
            field = (Square[][]) o;
            return field;
        } catch (IOException | ClassNotFoundException ex) {
            logger.warning(ex.toString());
        }

        return field;
    }
}
