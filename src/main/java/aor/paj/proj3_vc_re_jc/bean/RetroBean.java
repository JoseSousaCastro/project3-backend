package aor.paj.proj3_vc_re_jc.bean;


import aor.paj.proj3_vc_re_jc.dto.RetroCommentDTO;
import aor.paj.proj3_vc_re_jc.dto.RetroEventDTO;
import jakarta.ejb.Stateless;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbConfig;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class RetroBean {
    final String filename = "retrospectives.json";
    private ArrayList<RetroEventDTO> retroEvents;

    public RetroBean() {
        File f = new File(filename);
        if (f.exists()) {
            try {
                FileReader filereader = new FileReader(f);
                retroEvents = JsonbBuilder.create().fromJson(filereader, new ArrayList<RetroEventDTO>() {
                }.getClass().getGenericSuperclass());
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else
            retroEvents = new ArrayList<RetroEventDTO>();
    }

    public boolean addRetrospective(RetroEventDTO retroEvent) {
        boolean added = true;
        if (retroEvent.getTitle().isBlank() && retroEvent.getDate() == null) {
            added = false;
        } else {
            retroEvents.add(retroEvent);
            writeIntoJsonFile();
        }
        return added;
    }


    public boolean addCommentToRetrospective(String id, RetroCommentDTO retroComment) {
        boolean added = true;
        if (retroComment.getDescription().isBlank() && retroComment.getUsername() == null && !validateCommentCategory(retroComment)) {
            added = false;
        } else {
            for (RetroEventDTO a : retroEvents) {
                if (a.getId().equals(id)) {
                    a.addComment(retroComment);
                    writeIntoJsonFile();
                }
            }
        }
        return added;
    }

    public RetroEventDTO getRetrospective(String id) {
        RetroEventDTO retroEvent = null;
        for (RetroEventDTO a : retroEvents) {
            if (a.getId().equals(id)) {
                retroEvent = a;
            }
        }
        return retroEvent;
    }

    public ArrayList<RetroEventDTO> getRetrospectives() {
        return retroEvents;
    }

    public ArrayList<RetroCommentDTO> getComments(String id) {
        ArrayList<RetroCommentDTO> comments = null;
        for (RetroEventDTO a : retroEvents) {
            if (a.getId().equals(id)) {
                comments = a.getRetroComments();
            }
        }
        return comments;
    }

    public RetroCommentDTO getComment(String id, String commentId) {
        RetroCommentDTO comment = null;
        for (RetroEventDTO a : retroEvents) {
            if (a.getId().equals(id)) {
                for (RetroCommentDTO c : a.getRetroComments()) {
                    if (c.getCommentId().equals(commentId)) {
                        comment = c;
                    }
                }
            }
        }
        return comment;
    }

    public boolean editComment(String id, String commentId, RetroCommentDTO retroComment) {
        boolean edited = false;
        for (RetroEventDTO a : retroEvents) {
            if (a.getId().equals(id)) {
                for (RetroCommentDTO c : a.getRetroComments()) {
                    if (c.getCommentId().equals(commentId)) {
                        c.setDescription(retroComment.getDescription());
                        c.setCategory(retroComment.getCategory());
                        edited = true;
                        writeIntoJsonFile();
                    }
                }
            }
        }
        return edited;
    }

    public boolean validateCommentCategory(RetroCommentDTO retroComment) {
        boolean valid = true;
        if (retroComment.getCategory() != 100 && retroComment.getCategory() != 200 && retroComment.getCategory() != 300) {
            valid = false;
        }
        return valid;
    }


    public List<String> getMembers(String id) {
        List<String> members = null;
        for (RetroEventDTO a : retroEvents) {
            if (a.getId().equals(id)) {
                members = a.getRetroMembers();
            }
        }
        return members;
    }

    public String getMember(String id, String id2) {
        String member = null;
        for (RetroEventDTO a : retroEvents) {
            if (a.getId().equals(id)) {
                for (String c : a.getRetroMembers()) {
                    if (c.equals(id2)) {
                        member = c;
                    }
                }
            }
        }
        return member;
    }

    public boolean addMemberToRetrospective(String id, String id2) {
        boolean added = true;
        for (RetroEventDTO a : retroEvents) {
            if (a.getId().equals(id)) {
                a.addMember(id2);
                writeIntoJsonFile();
            }
        }
        return added;
    }

    public boolean deleteComment(String id, String id2) {
        boolean deleted = false;
        for (RetroEventDTO a : retroEvents) {
            if (a.getId().equals(id)) {
                for (RetroCommentDTO c : a.getRetroComments()) {
                    if (c.getCommentId().equals(id2)) {
                        a.getRetroComments().remove(c);
                        deleted = true;
                        writeIntoJsonFile();
                    }
                }
            }
        }
        return deleted;
    }

    public boolean deleteAllComments(String id) {
        boolean deleted = false;
        for (RetroEventDTO a : retroEvents) {
            if (a.getId().equals(id)) {
                a.getRetroComments().clear();
                deleted = true;
                writeIntoJsonFile();
            }
        }
        return deleted;
    }


    private void writeIntoJsonFile() {
        Jsonb jsonb = JsonbBuilder.create(new
                JsonbConfig().withFormatting(true));
        try {
            jsonb.toJson(retroEvents, new FileOutputStream(filename));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}