package ru.shmakova.hackathon.network.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by shmakova on 23.07.16.
 */

public class Lookup {

    @SerializedName("head")
    @Expose
    public Object head;
    @SerializedName("def")
    @Expose
    public List<Def> def = new ArrayList<Def>();

    public class Def {

        @SerializedName("text")
        @Expose
        public String text;
        @SerializedName("pos")
        @Expose
        public String pos;
        @SerializedName("tr")
        @Expose
        public List<Tr> tr = new ArrayList<Tr>();

    }

    public class Mean {

        @SerializedName("text")
        @Expose
        public String text;

    }

    public class Syn {

        @SerializedName("text")
        @Expose
        public String text;

    }

    public class Tr {

        @SerializedName("text")
        @Expose
        public String text;
        @SerializedName("pos")
        @Expose
        public String pos;
        @SerializedName("syn")
        @Expose
        public List<Syn> syn = new ArrayList<Syn>();
        @SerializedName("mean")
        @Expose
        public List<Mean> mean = new ArrayList<Mean>();
        @SerializedName("ex")
        @Expose
        public List<Ex> ex = new ArrayList<Ex>();

    }

    public class Ex {

        @SerializedName("text")
        @Expose
        public String text;
        @SerializedName("tr")
        @Expose
        public List<Tr_> tr = new ArrayList<Tr_>();

    }

    public class Tr_ {

        @SerializedName("text")
        @Expose
        public String text;

    }

}
