package aperture.service;

import aperture.model.TestSubject;
import aperture.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestSubjectService {

    private final RoomRepository roomRepository;

    @Autowired
    public TestSubjectService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public boolean allRoomsProvidedExist(TestSubject testSubject) {
        return testSubject.getRooms().stream()
            .allMatch(subjectRoom ->
                roomRepository.findById(subjectRoom.getId()) != null
                    && !roomRepository.findByName(subjectRoom.getName()).isEmpty()
                    && roomRepository.findByNumber(subjectRoom.getNumber()) != null
            );
    }

}
