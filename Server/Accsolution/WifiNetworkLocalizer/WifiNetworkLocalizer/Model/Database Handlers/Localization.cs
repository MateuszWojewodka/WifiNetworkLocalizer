using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using WifiNetworkLocalizer.Model.Message_Types;

namespace WifiNetworkLocalizer.Model.Database_Handlers
{
    public class Localization : ILocalization
    {
        public Point GetLocalizationPoint(ThreeMacIds threeMacIds)
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
    }
}
