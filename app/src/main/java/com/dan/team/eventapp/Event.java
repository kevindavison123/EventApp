package com.dan.team.eventapp;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.sql.Date;
import java.util.ArrayList;

/**
 * Created by Jakob on 3/9/2016.
 */
public class Event extends BackendParcelable
{
        private int eventId;
        private int authorId;
        private String description;
        private String title;
        private String location;
        private Date date;
        private Time time;
        private String imageExt;

        public Event(int eventId, int authorId, String description, String title, String location, Date date, Time time, String imageExt)
        {
            this.eventId = eventId;
            this.authorId = authorId;
            this.description = description;
            this.title = title;
            this.location = location;
            this.date = date;
            this.time = time;
            this.imageExt = imageExt;
        }

        public int getEventId() {
            return eventId;
        }

        public void setEventId(int eventId) {
            this.eventId = eventId;
        }

        public int getAuthorId() {
            return authorId;
        }

        public void setAuthorId(int authorId) {
            this.authorId = authorId;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public Time getTime() {
            return time;
        }

        public void setTime(Time time) {
            this.time = time;
        }

        public String getImageExt() {
            return imageExt;
        }

        public void setImageExt(String imageExt) {
            this.imageExt = imageExt;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Event event = (Event) o;

            if (eventId != event.eventId) return false;
            if (authorId != event.authorId) return false;
            if (!description.equals(event.description)) return false;
            if (!title.equals(event.title)) return false;
            if (!location.equals(event.location)) return false;
            if (!date.equals(event.date)) return false;
            if (!time.equals(event.time)) return false;
            return imageExt.equals(event.imageExt);

        }

        @Override
        public String toString() {
            return "Event{" +
                    "eventId=" + eventId +
                    ", authorId=" + authorId +
                    ", description='" + description + '\'' +
                    ", title='" + title + '\'' +
                    ", location='" + location + '\'' +
                    ", date=" + date +
                    ", time=" + time +
                    ", imageExt='" + imageExt + '\'' +
                    '}';
        }

    @Override
    public ArrayList<String> getParameters()
    {
        ArrayList<String> parameters = new ArrayList<String>();

        parameters.add(String.valueOf(getEventId()));
        parameters.add(String.valueOf(getAuthorId()));
        parameters.add(getDescription());
        parameters.add(getTitle());
        parameters.add(getLocation());
        parameters.add(getDate().toString());
        parameters.add(getTime().toString());
        parameters.add(getImageExt());

        return parameters;
    }


    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags)
    {
        out.writeInt(eventId);
        out.writeInt(authorId);
        out.writeString(description);
        out.writeString(title);
        out.writeString(location);
        out.writeValue(date);
        out.writeValue(time);
        out.writeString(imageExt);
    }


    public Parcelable.Creator<Event> CREATOR
            = new Parcelable.Creator<Event>()
    {
        public Event createFromParcel(Parcel in)
        {
            return new Event(in.readInt(),
                    in.readInt(),
                    in.readString(),
                    in.readString(),
                    in.readString(),
                    (Date)in.readValue(java.sql.Date.class.getClassLoader()),
                    (Time)in.readValue(java.sql.Time.class.getClassLoader()),
                    in.readString());
        }

        public Event[] newArray(int size)
        {
            return new Event[size];
        }
    };


    public static Event makeFromJSON(JSONObject response, Context context) throws JSONException
    {
        Event event;
        try
        {
            event = new Event(response.getInt(context.getString(R.string.Event_ID)),
                    response.getInt(context.getString(R.string.Event_autherID)),
                    response.getString(context.getString(R.string.Event_Description)),
                    response.getString(context.getString(R.string.Event_Title)),
                    response.getString(context.getString(R.string.Event_Location)),
                    (Date)response.get(context.getString(R.string.Event_Date)),
                    (Time)response.get(context.getString(R.string.Event_Time)),
                    response.getString(context.getString(R.string.Event_ImageEXT)));
        } catch (Exception e)
        {
            throw new JSONException("Parsing error: " + response.toString());
        }
        return event;
    }
}

