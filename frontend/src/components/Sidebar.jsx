function Sidebar() {
    return (
        <aside className="w-60 min-h-screen border-r border-gray-200 bg-white px-5 py-6">

            <div className="mb-10">
                <h1 className="text-xl font-bold text-gray-900">
                    RLO
                </h1>
                <p className="text-xs text-gray-500 mt-1">
                    Reverse Logistics
                </p>
            </div>

            <nav className="space-y-1">

                <p className="text-xs font-medium text-gray-400 uppercase tracking-wide mb-3">
                    Operations
                </p>

                <button className="w-full text-left px-3 py-2 rounded-md bg-gray-100 text-gray-900 font-medium">
                    Overview
                </button>

                <button className="w-full text-left px-3 py-2 rounded-md text-gray-600 hover:bg-gray-50">
                    Returns
                </button>

                <button className="w-full text-left px-3 py-2 rounded-md text-gray-600 hover:bg-gray-50">
                    Routes
                </button>

                <button className="w-full text-left px-3 py-2 rounded-md text-gray-600 hover:bg-gray-50">
                    Facilities
                </button>

                <button className="w-full text-left px-3 py-2 rounded-md text-gray-600 hover:bg-gray-50">
                    Vehicles
                </button>

                <button className="w-full text-left px-3 py-2 rounded-md text-gray-600 hover:bg-gray-50">
                    Optimization
                </button>

                <button className="w-full text-left px-3 py-2 rounded-md text-gray-600 hover:bg-gray-50">
                    History
                </button>

            </nav>

            <div className="absolute bottom-6 left-5 text-xs text-gray-400">
                API Status <span className="text-green-600">●</span> Connected
            </div>

        </aside>
    );
}

export default Sidebar;