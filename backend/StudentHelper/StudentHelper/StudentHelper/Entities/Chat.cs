using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;
using System.Collections.Generic;

namespace StudentHelper.Entities
{
    public class Chat
    {
        [BsonId]
        [BsonRepresentation(BsonType.ObjectId)]
        public string Id { get; set; }

        [BsonElement("prepodName")]
        public string prepodName { get; set; }
        [BsonElement("lessonName")]
        public string lessonName { get; set; }
        public string group { get; set; }
        public List<Message> messages { get; set; } = new List<Message>();
    }
    public class Message
    {
        public string time { get; set; }
        public string msg { get; set; }
    }

    public class ChatstoreDatabaseSettings : IChatstoreDatabaseSettings
    {
        public string ChatsCollectionName { get; set; }
        public string ConnectionString { get; set; }
        public string DatabaseName { get; set; }
    }

    public interface IChatstoreDatabaseSettings
    {
        string ChatsCollectionName { get; set; }
        string ConnectionString { get; set; }
        string DatabaseName { get; set; }
    }
}