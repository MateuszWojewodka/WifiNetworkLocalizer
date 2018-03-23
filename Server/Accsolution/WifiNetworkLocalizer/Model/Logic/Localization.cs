using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using WifiNetworkLocalizer.Model.Database_Entities;
using WifiNetworkLocalizer.Model.Message_Types;

namespace WifiNetworkLocalizer.Model.Database_Handlers
{
    public class Localization : ILocalization
    {
        public Point GetXYLocalizationPoint(ThreeMacIds threeMacIds)
        {
            throw new NotImplementedException();
        }

        public List<string> GetPossibleBuildings()
        {
            throw new NotImplementedException();
        }

        public ThreeMacIds GetThreeMeasurmentMacIds(string buildingName)
        {
            throw new NotImplementedException();
        }

        public void PutThreeMeasurmentPointsIntoDatabase(ThreeMacIds threeMacIds)
        {
            using (var ctx = new WifiLocalizerContext())
            {
                ctx.Database.Log += (message => Console.Write(message));

                ctx.ThreeMeasurmentMacIds.Add(threeMacIds);
                ctx.SaveChanges();

                Console.WriteLine("Rekord dodany w bazie danych.");
            }
        }
    }
}
