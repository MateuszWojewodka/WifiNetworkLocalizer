using Model.Database_Classes;
using Model.Entity_Classes;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;

namespace TestProject
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine("Press enter to start.");
            Console.ReadLine();

            HttpClient client = new HttpClient();
            client.BaseAddress = new Uri("http://localhost:1471/localization/rooms/");
            client.DefaultRequestHeaders.Accept.Clear();
            client.DefaultRequestHeaders.Accept.Add(
                new System.Net.Http.Headers.MediaTypeWithQualityHeaderValue("application/json"));

            try
            {
                //adding new room
                ThreeMacIds determinantMacIds = new ThreeMacIds
                {
                    FirstMacId = "first",
                    SecondMacId = "second",
                    ThirdMacId = "third"
                };
                
                PutNewRoomInfo(client, "MCHTR", determinantMacIds).Wait();
                Console.WriteLine("Added new room to db.");

                //getting rooms list
                Console.WriteLine("Room list:");
                List<RoomInfo> possibleRooms = GetPossibleRoomsAsync(client, "").Result;
                foreach (var item in possibleRooms)
                {
                    Console.WriteLine(item.roomId);
                    Console.WriteLine(item.roomName);
                    Console.WriteLine("*");
                }

                //adding new measurment point
                addMeasurmentPoint(client, 1, 1, 10, 10, 10);
                addMeasurmentPoint(client, 2, 2, 30, 30, 30);
                addMeasurmentPoint(client, 3, 3, 40, 40, 40);
                addMeasurmentPoint(client, 4, 4, 60, 60, 60);

                //get nearest measurment point
                Point nearestPoint = GetNearestXYLocalizationPoint(client, "1/point?firstMacId=-99&secondMacId=-2&thirdMacId=-55").GetAwaiter().GetResult();
                Console.WriteLine("Nearest Point:\n" + nearestPoint.x + "\n" + nearestPoint.y);
            } 
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }
        }

        static void addMeasurmentPoint(HttpClient client, int x, int y, int a, int b, int c)
        {
            RSSIMeasurmentPoint point = new RSSIMeasurmentPoint
            {
                FirstMacIdRSSI = a,
                SecondMacIdRSSI = b,
                ThirdMacIdRSSI = c,
                X = x,
                Y = y
            };

            AddRSSIMeasurmentPoint(client, "1/point", point).GetAwaiter().GetResult();
            Console.WriteLine("Added new measurment point.");
        }

        static async Task<List<RoomInfo>> GetPossibleRoomsAsync(HttpClient client, string path)
        {
            List<RoomInfo> rooms = null;
            HttpResponseMessage response = await client.GetAsync(path);

            if (response.IsSuccessStatusCode)
            {
                rooms = await response.Content.ReadAsAsync<List<RoomInfo>>();
            }
            return rooms;
        }

        static async Task<ThreeMacIds> GetThreeMacIds(HttpClient client, string path)
        {
            ThreeMacIds macIds = null;
            HttpResponseMessage response = await client.GetAsync(path);

            if (response.IsSuccessStatusCode)
            {
                macIds = await response.Content.ReadAsAsync<ThreeMacIds>();
            }
            return macIds;
        }

        static async Task PutNewRoomInfo(HttpClient client, string path, ThreeMacIds determinantMacIds)
        {
            HttpResponseMessage response = await client.PutAsJsonAsync(path, determinantMacIds);
            response.EnsureSuccessStatusCode();
        }

        static async Task AddRSSIMeasurmentPoint(HttpClient client, string path, RSSIMeasurmentPoint rssi)
        {
            HttpResponseMessage response = await client.PostAsJsonAsync
                (path, new
            {
                FirstMacIdRSSI = rssi.FirstMacIdRSSI,
                SecondMacIdRSSI = rssi.SecondMacIdRSSI,
                ThirdMacIdRSSI = rssi.ThirdMacIdRSSI,
                X = rssi.X,
                Y = rssi.Y
            });

            response.EnsureSuccessStatusCode();
        }

        static async Task<Point> GetNearestXYLocalizationPoint(HttpClient client, string path)
        {
            Point point = null;
            HttpResponseMessage response = await client.GetAsync(path);

            if (response.IsSuccessStatusCode)
            {
                point = await response.Content.ReadAsAsync<Point>();
            }
            return point;
        }
    }
}
